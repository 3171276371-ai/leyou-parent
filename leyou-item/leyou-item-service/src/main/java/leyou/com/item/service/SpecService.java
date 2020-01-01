package leyou.com.item.service;

import leyou.com.item.pojo.TbSpecGroup;
import leyou.com.item.pojo.TbSpecParam;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/9 15:15
 */
public interface SpecService {

    /**
     * 根据cid查询商品规格参数组
     * @param cid
     * @return
     */
    List<TbSpecGroup> querySpecGroup(Long cid);

    /**
     * 根据gid查询商品参数详情
     * @param gid
     * @return
     */
    List<TbSpecParam> querySpecParam(Long gid, Long cid, Boolean generic, Boolean searching);

    /**
     * 添加商品规格
     * @param tbSpecGroup
     */
    void addSpecGroup(TbSpecGroup tbSpecGroup);

    /**
     * 根据商品id删除商品
     * @param id
     */
    void delete(Long id);

    /**
     * 修改商品规格
     * @param tbSpecGroup
     */
    void updateSpecGroup(TbSpecGroup tbSpecGroup);

    /**
     * 添加规格详情
     * @param tbSpecParam
     */
    void saveParam(TbSpecParam tbSpecParam);

    /**
     * 根据商品分类查询商品规格
     * @param cid
     * @return
     */
    List<TbSpecParam>  queryParamByCid(Long cid);

    /**
     * 根据分类id查询商品
     * @param cid
     * @return
     */
    List<TbSpecGroup> querySpecsByCid(Long cid);
}
