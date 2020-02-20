package leyou.com.item.controller;

import leyou.com.item.pojo.SpuBo;
import leyou.com.item.pojo.TbCategory;
import leyou.com.item.pojo.TbSku;
import leyou.com.item.pojo.TbSpu;
import leyou.com.item.service.CategoryService;
import leyou.com.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/16 17:54
 * @Modeified By:
 */
@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 保存商品信息
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    @DeleteMapping("goods/delete/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id")Long id){
        goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新商品信息
     * @param spuBo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 根据3级分类id，查询1~3级的分类
     * @param id
     * @return
     */
    @GetMapping("all/level")
    public ResponseEntity<List<TbCategory>> queryAllByCid3(@RequestParam("id") Long id){
        List<TbCategory> list = this.categoryService.queryAllByCid3(id);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
    /**
     * 根据pid查询商品
     * @param pid
     * @return
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<TbSpu> querySpuById(@PathVariable("id")Long pid){
        TbSpu tbSpu =  goodsService.querySpuById(pid);
        if (tbSpu==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tbSpu);
    }

    /**
     * 根据skuid查询商品
     * @param pid
     * @return
     */
    @GetMapping("sku/{id}")
    public ResponseEntity<TbSku> querySkuById(@PathVariable("id")Long pid){
        TbSku tbSku =  goodsService.querySkuById(pid);
        if (tbSku==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tbSku);
    }


}
