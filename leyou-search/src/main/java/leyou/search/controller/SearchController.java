package leyou.search.controller;

import leyou.search.pojo.SearchRequest;
import leyou.search.pojo.SearchResult;
import leyou.search.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/23 13:38
 * @Modeified By:
 */
@Controller
public class SearchController {

    @Autowired
    private GoodsService goodsService;
    @PostMapping("page")
    public ResponseEntity<SearchResult> queryPage(@RequestBody SearchRequest searchRequest){
        SearchResult pageResult =  goodsService.queryPage(searchRequest);
        if (pageResult==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }
}
