package leyou.com.item.controller;

import leyou.com.item.pojo.TbBrand;
import leyou.com.item.service.BrandService;
import leyou.com.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/5 17:21
 */
@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<TbBrand>> queryByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc){
        PageResult<TbBrand> brandPageResult =  brandService.queryByPage(key,page,rows,sortBy,desc);
        if (CollectionUtils.isEmpty(brandPageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Void> saveBrand(TbBrand brand, @RequestParam(value = "cids", required = true)List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品品牌
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping("/save")
    public ResponseEntity<Void> updateBrand(TbBrand brand, @RequestParam(value = "cids", required = true)List<Long> cids){
        brandService.updateBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类id查询品牌列表
     * @param bid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<TbBrand>> queryBrandByCateGoryById(@PathVariable(value = "cid")Long bid){
            List<TbBrand> brand = brandService.queryByCid(bid);
            if (CollectionUtils.isEmpty(brand)){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(brand);

    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<TbBrand> queryBrandById(@PathVariable("id")Long id){
        TbBrand tbBrand = brandService.queryById(id);
        if (tbBrand==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tbBrand);
    }

}
