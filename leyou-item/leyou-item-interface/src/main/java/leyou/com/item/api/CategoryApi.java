package leyou.com.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 13:09
 * @Modeified By:
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根据id查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    List<String> queryNamesByIds(@RequestParam("ids")List<Long> ids);
}
