package leyou.com.item.dao;

import leyou.com.item.pojo.TbCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/4 18:00
 */
public interface CategoryDao extends Mapper<TbCategory> , SelectByIdListMapper<TbCategory, Long> {

    @Select("SELECT*from tb_category WHERE id=(SELECT category_id from tb_category_brand WHERE brand_id=#{bid});")
    TbCategory QueryByBid(@Param("bid") Long bid);
}
