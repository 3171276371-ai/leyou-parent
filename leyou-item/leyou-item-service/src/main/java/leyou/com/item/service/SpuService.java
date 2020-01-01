package leyou.com.item.service;

import leyou.com.item.pojo.SpuBo;
import leyou.com.item.pojo.TSpuDetail;
import leyou.com.item.pojo.TbSpu;
import leyou.com.pojo.PageResult;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/11 20:56
 */
public interface SpuService {
    /**
     * 查询商品列表
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    PageResult<SpuBo> queryByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 根据商品id查询商品细节
     * @param id
     * @return
     */
    TSpuDetail findDetailsById(Long id);

    /**
     * 修改商品的上下架情况
     * @param saleable
     */
    void changeSaleable(Long id,Boolean saleable);

}
