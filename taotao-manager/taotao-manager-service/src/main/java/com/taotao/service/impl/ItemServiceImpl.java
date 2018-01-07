package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        TbItemExample example = new TbItemExample();
        EUDataGridResult euDataGridResult = new EUDataGridResult();
        PageHelper.startPage(page, rows);
        List<TbItem> results = itemMapper.selectByExample(example);
        euDataGridResult.setRows(results);
        PageInfo<TbItem> pageInfo = new PageInfo<>(results);
        euDataGridResult.setTotal(pageInfo.getTotal());
        return euDataGridResult;
    }
}
