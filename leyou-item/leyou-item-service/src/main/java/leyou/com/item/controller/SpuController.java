package leyou.com.item.controller;

import leyou.com.item.pojo.*;
import leyou.com.item.service.SkuService;
import leyou.com.item.service.SpuService;
import leyou.com.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/11 20:37
 */
@Controller
public class SpuController {
    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;
    /**
     * 获取商品信息
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(@RequestParam(value = "key", required = false) String key,
                                                            @RequestParam(value = "saleable", required = true, defaultValue = "true") Boolean saleable,
                                                            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                                            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<SpuBo> spuPageResult = spuService.queryByPage(key, saleable, page, rows);
        if (CollectionUtils.isEmpty(spuPageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuPageResult);
    }

    /**
     * 根据商品id查询商品细节
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<TSpuDetail> findDetailsById(@PathVariable("id") Long id){
        TSpuDetail TSpuDetail = spuService.findDetailsById(id);
        if (TSpuDetail ==null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TSpuDetail);
    }

    /**
     * 根据spu的id查询sku
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<TbSku>> queryTbSkuBySpuId(@RequestParam(name = "id")Long id) {
        List<TbSku> tbSkuList = skuService.queryTbSkuBySpuId(id);
        if (CollectionUtils.isEmpty(tbSkuList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tbSkuList);
    }

    /**
     * 修改商品的上下架情况
     * @param saleable
     * @return
     */
    @PutMapping("spu/updateSaleable")
    public ResponseEntity<Void> changeSaleable(@RequestParam(value = "saleable")Boolean saleable,@RequestParam(value = "id")Long id){
        spuService.changeSaleable(id,saleable);
        return ResponseEntity.ok().build();
    }

}
