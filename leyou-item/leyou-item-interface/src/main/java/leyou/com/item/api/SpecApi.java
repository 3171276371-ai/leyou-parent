package leyou.com.item.api;

import leyou.com.item.pojo.TSpuDetail;
import leyou.com.item.pojo.TbSpecGroup;
import leyou.com.item.pojo.TbSpecParam;
import leyou.com.item.pojo.TbSpu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/9 15:16
 */

@RequestMapping("spec")
public interface SpecApi {

    /**
     * 根据gid查询商品规格参数详情
     *
     * @param gid
     * @return
     */
    @GetMapping("params")
    List<TbSpecParam> querySpecParam(@RequestParam(value = "gid", required = false) Long gid,
                                     @RequestParam(value = "cid", required = false) Long cid,
                                     @RequestParam(value = "generic", required = false) Boolean generic,
                                     @RequestParam(value = "searching", required = false) Boolean searching);

    /**
     * 根据分类id查询商品规格以及规格组
     *
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    List<TbSpecGroup> queryParamGroupByCid(@PathVariable("cid") Long cid);

    /**
     * 根据商品id查询商品细节
     *
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    TSpuDetail findDetailsById(@PathVariable("id") Long id);


}
