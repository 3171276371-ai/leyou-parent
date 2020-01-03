package cxt.cn.goods.controller;

import cxt.cn.goods.service.GoodsHtmlService;
import cxt.cn.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/28 15:10
 * @Modeified By:
 */
@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;
    /**
     * 跳转到商品详情页
     * @param model
     * @param id
     * @return
     */
    @GetMapping("{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long id){
        Map<String, Object> stringObjectMap = goodsService.loadData(id);
        model.addAllAttributes(stringObjectMap);
        goodsHtmlService.asyncExcute(id);
        return "item";
    }
}
