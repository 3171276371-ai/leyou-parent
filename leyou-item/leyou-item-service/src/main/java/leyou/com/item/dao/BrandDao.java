package leyou.com.item.dao;

import leyou.com.item.pojo.TbBrand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/5 17:19
 */
public interface BrandDao extends Mapper<TbBrand> {

    @Insert("insert into leyou.tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<TbBrand> selectBrandByCid(Long cid);
}
