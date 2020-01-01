package leyou.com.item.service;

import leyou.com.item.pojo.TbBrand;
import leyou.com.pojo.PageResult;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/5 17:20
 */
public interface BrandService {

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<TbBrand> queryByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    void saveBrand(TbBrand brand, List<Long> cids);

    /**
     * 修改品牌
     * @param brand
     * @param cids
     */
    void updateBrand(TbBrand brand, List<Long> cids);

    /**
     * 根据id查询品牌
     * @param cid
     * @return
     */
    List<TbBrand> queryByCid(Long cid);

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    TbBrand queryById(Long id);
}
