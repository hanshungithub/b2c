package com.taotao.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper paramMapper;

    @Override
    public TaotaoResult getItemParamByCid(long cid) {

        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);

        List<TbItemParam> tbItemParamItems = paramMapper.selectByExampleWithBLOBs(example);

        log.info("查询出来的结果数为-->{}" + tbItemParamItems);

        if (tbItemParamItems != null && tbItemParamItems.size() > 0) {
            return TaotaoResult.ok(tbItemParamItems.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        return TaotaoResult.ok(paramMapper.insert(itemParam));
    }
}
