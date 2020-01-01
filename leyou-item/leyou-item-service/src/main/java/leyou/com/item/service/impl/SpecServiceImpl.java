package leyou.com.item.service.impl;

import leyou.com.item.dao.SpacGroupDao;
import leyou.com.item.dao.SpecPramDao;
import leyou.com.item.pojo.TbSpecGroup;
import leyou.com.item.pojo.TbSpecParam;
import leyou.com.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/9 15:15
 */
@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpacGroupDao spacGroupDao;
    @Autowired
    private SpecPramDao specPramDao;
    /**
     * 根据cid查询商品规格参数组
     * @param cid
     * @return
     */
    @Override
    public List<TbSpecGroup> querySpecGroup(Long cid) {
        TbSpecGroup specGroup = new TbSpecGroup();
        specGroup.setCid(cid);
        return this.spacGroupDao.select(specGroup);
    }

    /**
     * 根据gid查询规格参数
     * @param gid
     * @return
     */
    public List<TbSpecParam> querySpecParam(Long gid, Long cid, Boolean generic, Boolean searching) {
        TbSpecParam record = new TbSpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.specPramDao.select(record);
    }

    /**
     * 添加商品规格组
     * @param tbSpecGroup
     */
    @Transactional
    @Override
    public void addSpecGroup(TbSpecGroup tbSpecGroup) {
        spacGroupDao.insert(tbSpecGroup);
    }

    /**
     * 根据商品id删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        TbSpecGroup tbSpecGroup = new TbSpecGroup();
        tbSpecGroup.setId(id);
        spacGroupDao.delete(tbSpecGroup);
    }

    /**
     * 修改商品规格
     * @param tbSpecGroup
     */
    @Override
    public void updateSpecGroup(TbSpecGroup tbSpecGroup) {
        Example example = new Example(TbSpecGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",tbSpecGroup.getId());
        spacGroupDao.updateByExampleSelective(tbSpecGroup,example);
    }

    @Transactional
    @Override
    public void saveParam(TbSpecParam tbSpecParam) {
        specPramDao.insert(tbSpecParam);
    }

    @Override
    public  List<TbSpecParam> queryParamByCid(Long cid) {
        TbSpecParam tbSpecParam = new TbSpecParam();
        tbSpecParam.setCid(cid);
        return specPramDao.select(tbSpecParam);
    }

    @Override
    public List<TbSpecGroup> querySpecsByCid(Long cid) {
        List<TbSpecGroup> groups = this.querySpecGroup(cid);
         groups.forEach(g -> {
            // 查询组内参数
            g.setTbSpecParams(this.querySpecParam(g.getId(), null, null, null));
        });
        return groups;
    }
}
