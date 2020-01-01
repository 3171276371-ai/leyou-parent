package leyou.search.pojo;

import leyou.com.item.pojo.TbBrand;
import leyou.com.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/25 13:38
 * @Modeified By:
 */

public class SearchResult extends PageResult<Goods> {
    private List<Map<String,Object>> categories;
    private List<TbBrand> brands;
    private List<Map<String,Object>> specs;


    public SearchResult(List<Map<String, Object>> categories, List<TbBrand> brands) {
        this.categories = categories;
        this.brands = brands;
    }

    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Map<String, Object>> categories, List<TbBrand> brands,List<Map<String,Object>>specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(Long total, List<Goods> items, List<Map<String, Object>> categories, List<TbBrand> brands) {
        super(total, items);
        this.categories = categories;
        this.brands = brands;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }

    public List<TbBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<TbBrand> brands) {
        this.brands = brands;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }
}
