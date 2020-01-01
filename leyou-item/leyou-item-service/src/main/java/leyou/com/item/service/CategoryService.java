package leyou.com.item.service;

import leyou.com.item.pojo.TbCategory;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/4 18:01
 */
public interface CategoryService {

    /**
     * 根据pid查询
     *
     * @param pid
     * @return
     */
    List<TbCategory> queryCategoryByPId(Long pid);

    /**
     * 根据bid查询
     *
     * @param id
     * @return
     */
    TbCategory queryByBrandId(Long id);

    /**
     * 删除节点
     *
     * @param id
     */
    void deleteNode(Long id);

    /**
     * 通过id查询名子
     * @param ids
     * @return
     */
    List<String> queryNamesByIds(List<Long> ids);

    /**
     * 根据3级分类id，查询1~3级的分类
     * @param id
     * @return
     */
    List<TbCategory> queryAllByCid3(Long id);
}
