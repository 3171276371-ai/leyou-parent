package leyou.com.item.api;

import leyou.com.item.pojo.SpuBo;
import leyou.com.item.pojo.TSpuDetail;
import leyou.com.item.pojo.TbSku;
import leyou.com.item.pojo.TbSpu;
import leyou.com.pojo.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 13:17
 * @Modeified By:
 */

public interface GoodsApi {
    /**
     * 获取商品信息
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    PageResult<SpuBo> querySpuByPage(@RequestParam(value = "key", required = false) String key,
                                     @RequestParam(value = "saleable", required = true, defaultValue = "true") Boolean saleable,
                                     @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * 根据商品id查询商品细节
     *
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    TSpuDetail findDetailsById(@PathVariable("id") Long id);

    /**
     * 根据spu的id查询sku
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    List<TbSku> queryTbSkuBySpuId(@RequestParam("id")Long id);

    /**
     * 根据pid查询商品
     *
     * @param pid
     * @return
     */
    @GetMapping("spu/{id}")
    TbSpu querySpuById(@PathVariable("id") Long pid);
}
