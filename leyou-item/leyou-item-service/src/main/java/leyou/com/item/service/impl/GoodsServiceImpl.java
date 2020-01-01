package leyou.com.item.service.impl;

import leyou.com.item.dao.*;
import leyou.com.item.pojo.*;
import leyou.com.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/16 18:01
 * @Modeified By:
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SpuDetailDao spuDetailDao;
    @Autowired
    private SpuDao spuDao;
    @Autowired
    private StockDao stockDao;
    @Transactional
    @Override
    public void saveGoods(SpuBo spuBo) {
        // 新增spu
        // 设置默认字段
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuDao.insertSelective(spuBo);

        // 新增spuDetail
        TSpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailDao.insertSelective(spuDetail);

        saveSkuAndStock(spuBo);
    }

    @Override
    public void deleteGoods(Long id) {
        TbSpu tbSpu = new TbSpu();
        tbSpu.setId(id);
        spuDao.delete(tbSpu);
    }
    @Transactional
    @Override
    public void updateGoods(SpuBo spu) {
        // 查询以前sku
        List<TbSku> skus = this.querySkuBySpuId(spu.getId());
        // 如果以前存在，则删除
        if(!CollectionUtils.isEmpty(skus)) {
            List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            // 删除以前库存
            Example example = new Example(TbStock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockDao.deleteByExample(example);

            // 删除以前的sku
            TbSku record = new TbSku();
            record.setSpuId(spu.getId());
            this.skuDao.delete(record);

        }
        // 新增sku和库存
        saveSkuAndStock(spu);

        // 更新spu
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spu.setValid(null);
        spu.setSaleable(null);
        this.spuDao.updateByPrimaryKeySelective(spu);

        // 更新spu详情
        this.spuDetailDao.updateByPrimaryKeySelective(spu.getSpuDetail());
    }

    private List<TbSku> querySkuBySpuId(Long id) {
        TbSku tbSku = new TbSku();
        tbSku.setSpuId(id);
        return skuDao.select(tbSku);
    }

    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuDao.insertSelective(sku);

            // 新增库存
            TbStock stock = new TbStock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockDao.insertSelective(stock);
        });
    }

    @Override
    public TbSpu querySpuById(Long pid) {
        return  spuDao.selectByPrimaryKey(pid);
    }
}
