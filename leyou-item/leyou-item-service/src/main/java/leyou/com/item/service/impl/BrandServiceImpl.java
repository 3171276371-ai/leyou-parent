package leyou.com.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import leyou.com.item.dao.BrandDao;
import leyou.com.item.pojo.TbBrand;
import leyou.com.item.service.BrandService;
import leyou.com.pojo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/5 17:20
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public PageResult<TbBrand> queryByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //开启模糊查询
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)){
        criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<TbBrand> tbBrands = brandDao.selectByExample(example);
        // 包装成pageInfo
        PageInfo<TbBrand> pageInfo = new PageInfo<>(tbBrands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Transactional
    @Override
    public void saveBrand(TbBrand brand, List<Long> cids) {
        brandDao.insertSelective(brand);
        cids.forEach(cid -> {
            this.brandDao.insertCategoryAndBrand(cid, brand.getId());});
    }

    @Override
    public void updateBrand(TbBrand brand, List<Long> cids) {
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",brand.getId());
        brandDao.updateByExample(brand,example);
    }

    @Override
    public List<TbBrand> queryByCid(Long cid) {

        return this.brandDao.selectBrandByCid(cid);
    }

    @Override
    public TbBrand queryById(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }


}
