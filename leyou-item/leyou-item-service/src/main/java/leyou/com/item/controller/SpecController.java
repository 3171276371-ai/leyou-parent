package leyou.com.item.controller;

import leyou.com.item.pojo.TbSpecGroup;
import leyou.com.item.pojo.TbSpecParam;
import leyou.com.item.service.SpecService;
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
 * @CreateTime: 2019/12/9 15:16
 */
@Controller
@RequestMapping("spec")
public class SpecController {
    @Autowired
    private SpecService specService;

    /**
     * 根据cid查询商品规格参数组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<TbSpecGroup>> querySpecGroup(@PathVariable("cid") Long cid) {
        List<TbSpecGroup> list = specService.querySpecGroup(cid);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据gid查询商品规格参数详情
     *
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<TbSpecParam>> querySpecParam(@RequestParam(value = "gid",required = false) Long gid,
                                                            @RequestParam(value = "cid", required = false)Long cid,
                                                            @RequestParam(value = "generic", required = false)Boolean generic,
                                                            @RequestParam(value = "searching", required = false)Boolean searching) {
        List<TbSpecParam> params = this.specService.querySpecParam(gid, cid, generic, searching);

        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 添加商品规格
     *
     * @param tbSpecGroup
     * @return
     */
    @PostMapping("group/save")
    public ResponseEntity<Void> addSpecGroup(@RequestBody TbSpecGroup tbSpecGroup) {
        specService.addSpecGroup(tbSpecGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品规格组
     *
     * @param tbSpecGroup
     * @return
     */
    @PutMapping("group/save")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody TbSpecGroup tbSpecGroup) {
        try {
            specService.updateSpecGroup(tbSpecGroup);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据商品id删除商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("group/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        specService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 添加规格详情
     *
     * @param tbSpecParam
     * @return
     */
    @PostMapping("param/save")
    public ResponseEntity<Void> saveParam(@RequestBody TbSpecParam tbSpecParam) {
        specService.saveParam(tbSpecParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类查询规格参数
     */
    @GetMapping("queryByCid")
    public ResponseEntity<List<TbSpecParam>> queryPaRAMByCid(@RequestParam(value = "cid") Long cid) {
        List<TbSpecParam> tbSpecParams = specService.queryParamByCid(cid);
        if (tbSpecParams == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tbSpecParams);

    }

    /**
     * 根据分类id查询商品规格以及规格组
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    public ResponseEntity<List<TbSpecGroup>> queryParamGroupByCid(@PathVariable("cid") Long cid){
        List<TbSpecGroup> list = this.specService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
