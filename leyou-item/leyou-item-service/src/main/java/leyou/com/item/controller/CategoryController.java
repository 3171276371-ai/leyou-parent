package leyou.com.item.controller;

import leyou.com.item.pojo.TbCategory;
import leyou.com.item.service.CategoryService;
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
 * @CreateTime: 2019/12/4 18:05
 */
@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父id查询子节点
     *
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<TbCategory>> queryCategoryByPId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        try {
            if (pid == null || pid < 0) {
                return ResponseEntity.badRequest().build();
            }
            List<TbCategory> categories = categoryService.queryCategoryByPId(pid);
            if (CollectionUtils.isEmpty(categories)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * 根据品牌信息查询商品分类
     */
    @GetMapping("/bid/{id}")
    public ResponseEntity<TbCategory> queryByBrandId(@PathVariable("id") Long id) {
        TbCategory categories = categoryService.queryByBrandId(id);
        if (categories == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * 删除一个节点
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategoryNode(@PathVariable("id") Long id) {
        categoryService.deleteNode(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据id查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }


}
