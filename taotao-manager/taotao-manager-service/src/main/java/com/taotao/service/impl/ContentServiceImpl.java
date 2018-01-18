package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbItem;
import com.taotao.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EUDataGridResult getContetList(int page, int rows, Long categoryId) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //分页参数设置
        EUDataGridResult euDataGridResult = new EUDataGridResult();
        PageHelper.startPage(page, rows);
        List<TbContent> result = contentMapper.selectByExample(example);
        euDataGridResult.setRows(result);
        PageInfo<TbContent> pageInfo = new PageInfo<>(result);
        euDataGridResult.setTotal(pageInfo.getTotal());
        return euDataGridResult;
    }
}
