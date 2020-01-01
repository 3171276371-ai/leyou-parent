package cxt.cn.goods.service;

import cxt.cn.goods.client.BrandClient;
import cxt.cn.goods.client.CategoryClient;
import cxt.cn.goods.client.GoodsClient;
import cxt.cn.goods.client.SpecClient;
import leyou.com.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/28 19:01
 * @Modeified By:
 */
@Service
public class GoodsService {

    @Autowired
    private BrandClient brandClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private CategoryClient categoryClient;

    public Map<String, Object> loadData(Long pid) {
        Map<String, Object> map = new HashMap<>();
        TbSpu tbSpu = this.goodsClient.querySpuById(pid);

        // 查询spudetail
        TSpuDetail detailsById = this.goodsClient.findDetailsById(pid);

        // 查询sku集合
        List<TbSku> tbSkus = this.goodsClient.queryTbSkuBySpuId(pid);

        // 查询分类
        List<Long> cids = Arrays.asList(tbSpu.getCid1(), tbSpu.getCid2(), tbSpu.getCid3());
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        List<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", cids.get(i));
            categoryMap.put("name", names.get(i));
            categories.add(categoryMap);
        }

        // 查询品牌
        TbBrand tbBrand = this.brandClient.queryBrandById(tbSpu.getBrandId());

        // 查询规格参数组
        List<TbSpecGroup> groups = this.specClient.queryParamGroupByCid(tbSpu.getCid3());

        // 查询特殊的规格参数
        List<TbSpecParam> params = this.specClient.querySpecParam(null, tbSpu.getCid3(), null, null);
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(), param.getName());
        });

        // 封装spu
        map.put("spu", tbSpu);
        // 封装spuDetail
        map.put("spuDetail", detailsById);
        // 封装sku集合
        map.put("skus", tbSkus);
        // 分类
        map.put("categories", categories);
        // 品牌
        map.put("brand", tbBrand);
        // 规格参数组
        map.put("groups", groups);
        // 查询特殊规格参数
        map.put("paramMap", paramMap);

        return map;
    }
}
