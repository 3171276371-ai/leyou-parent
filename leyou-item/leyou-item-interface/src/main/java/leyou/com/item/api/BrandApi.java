package leyou.com.item.api;

import leyou.com.item.pojo.TbBrand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/5 17:21
 */

@RequestMapping("brand")
public interface BrandApi {

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    TbBrand queryBrandById(@PathVariable("id")Long id);
}
