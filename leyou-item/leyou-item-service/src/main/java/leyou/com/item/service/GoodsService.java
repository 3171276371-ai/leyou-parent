package leyou.com.item.service;

import leyou.com.item.pojo.SpuBo;
import leyou.com.item.pojo.TbSku;
import leyou.com.item.pojo.TbSpu;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/16 18:01
 * @Modeified By:
 */
public interface GoodsService {
    /**
     * 保存商品信息
     * @param spuBo
     */
    void saveGoods(SpuBo spuBo);

    /**
     * 删除商品信息
     * @param id
     */
    void deleteGoods(Long id);

    /**
     * 更新商品信息
     * @param spuBo
     */
    void updateGoods(SpuBo spuBo);

    /**
     * 根据pid查询商品
     * @param pid
     * @return
     */
    TbSpu querySpuById(Long pid);

    /**
     * 根据sku查询商品详情
     * @param skuId
     * @return
     */
    TbSku querySkuById(Long skuId);
}
