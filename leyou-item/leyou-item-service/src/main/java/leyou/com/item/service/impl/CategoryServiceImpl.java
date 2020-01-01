package leyou.com.item.service.impl;

import leyou.com.item.dao.CategoryDao;
import leyou.com.item.pojo.TbCategory;
import leyou.com.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/4 18:01
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 根据pid查询
     *
     * @param pid
     * @return
     */
    @Override
    public List<TbCategory> queryCategoryByPId(Long pid) {
        TbCategory tbCategory = new TbCategory();
        tbCategory.setParentId(pid);
        return categoryDao.select(tbCategory);
    }

    @Override
    public TbCategory queryByBrandId(Long id) {
        return categoryDao.QueryByBid(id);

    }

    @Override
    public void deleteNode(Long id) {
        TbCategory tbCategory = new TbCategory();
        tbCategory.setId(id);
        categoryDao.delete(tbCategory);
    }

    public List<String> queryNamesByIds(List<Long> ids) {
        List<TbCategory> list = this.categoryDao.selectByIdList(ids);
        List<String> names = new ArrayList<>();
        for (TbCategory category : list) {
            names.add(category.getName());
        }
        return names;
        // return list.stream().map(category -> category.getName()).collect(Collectors.toList());
    }

    @Override
    public List<TbCategory> queryAllByCid3(Long id) {
        TbCategory c3 = this.categoryDao.selectByPrimaryKey(id);
        TbCategory c2 = this.categoryDao.selectByPrimaryKey(c3.getParentId());
        TbCategory c1 = this.categoryDao.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1,c2,c3);

    }
}
