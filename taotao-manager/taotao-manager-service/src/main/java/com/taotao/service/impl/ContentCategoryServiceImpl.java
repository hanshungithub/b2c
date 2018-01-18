package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {
        //根据parentid查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一个节点
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");

            resultList.add(node);
        }
        return resultList;
    }

    /**
     * 添加节点
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult insertContentCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.insert(contentCategory);

        //在设置子节点后要查看父节点是否为true
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        return TaotaoResult.ok(contentCategory);
    }

    /**
     * 删除节点
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteContentCategory(Long parentId, Long id) {
        //1首先判断是不是为父节点，如果为父节点侧应该删除父节点及父节点的子节点
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);

        if (contentCategory.getIsParent()) {
            //查找所有的子节点
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory category : categoryList) {
                Long pId = category.getParentId();
                Long sunId = category.getId();
                TbContentCategory sunCategory = contentCategoryMapper.selectByPrimaryKey(sunId);
                if(!sunCategory.getIsParent()){
                    contentCategoryMapper.deleteByPrimaryKey(sunCategory.getId());
                }else {
                    deleteContentCategory(pId, sunId);
                }
            }
        }else {
            contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
            //判断父节点有几个子节点，如果是一个字parentId = false
            //查找父节点所有的子节点
            if (parentId != null) {
                TbContentCategoryExample example = new TbContentCategoryExample();
                TbContentCategoryExample.Criteria criteria = example.createCriteria();
                criteria.andParentIdEqualTo(parentId);
                List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
                if (categoryList != null && categoryList.size() == 1) {
                    TbContentCategory parentCatgory = new TbContentCategory();
                    parentCatgory.setIsParent(false);
                    parentCatgory.setId(parentId);
                    contentCategoryMapper.updateByPrimaryKey(parentCatgory);
                }
            }
        }
        return TaotaoResult.ok();
    }

    /**
     * 更新节点
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setId(id);
        category.setName(name);
        category.setUpdated(new Date());
        int result = contentCategoryMapper.updateByPrimaryKeySelective(category);
        if (result == 0) {
            return TaotaoResult.build(202, "更新失败");
        }else {
            return TaotaoResult.ok();
        }
    }

}
