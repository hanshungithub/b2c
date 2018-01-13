package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

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

    @Override
    public TaotaoResult createItem(TbItem tbItem,String desc,String itemParam) throws Exception {
        long itemId = IDUtils.genItemId();

        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());

        itemMapper.insert(tbItem);

        TaotaoResult taotaoResult = insertItemDesc(itemId, desc);

        //保存商品的规格参数
        TaotaoResult itemParamItem = insertItemParamItem(itemId, itemParam);
        if (taotaoResult.getStatus() != 200 || itemParamItem.getStatus() != 200) {
            throw new Exception("添加商品出错！");
        }else {
            return TaotaoResult.ok();
        }
    }

    private TaotaoResult insertItemDesc(Long id, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    private TaotaoResult insertItemParamItem(Long id, String itemParam) {
        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setItemId(id);
        paramItem.setCreated(new Date());
        paramItem.setUpdated(new Date());
        paramItem.setParamData(itemParam);
        itemParamItemMapper.insert(paramItem);
        return TaotaoResult.ok();
    }
}
