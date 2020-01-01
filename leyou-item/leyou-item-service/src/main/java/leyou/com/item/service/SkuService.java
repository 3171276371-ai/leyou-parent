package leyou.com.item.service;

import leyou.com.item.pojo.TbSku;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/11 22:30
 */
public interface SkuService {
    /**
     * 根据spu的id查询sku
     * @param id
     * @return
     */
    List<TbSku> queryTbSkuBySpuId(Long id);
}
