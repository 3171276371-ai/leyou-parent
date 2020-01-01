package leyou.com.item.service.impl;

import leyou.com.item.dao.SkuDao;
import leyou.com.item.dao.StockDao;
import leyou.com.item.pojo.TbSku;
import leyou.com.item.pojo.TbStock;
import leyou.com.item.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/11 22:31
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockDao stockDao;
    @Override
    public List<TbSku> queryTbSkuBySpuId(Long id) {
        TbSku sku = new TbSku();
        sku.setSpuId(id);
        List<TbSku> skus = this.skuDao.select(sku);
        return skus;
    }
}
