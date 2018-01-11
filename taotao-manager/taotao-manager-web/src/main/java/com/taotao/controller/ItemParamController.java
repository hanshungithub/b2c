package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
@Slf4j
public class ItemParamController {

    @Autowired
    private ItemParamService paramService;

    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
        TaotaoResult itemParamByCid = paramService.getItemParamByCid(itemCatId);
        return itemParamByCid;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid,String paramData) {
        TbItemParam param = new TbItemParam();
        param.setItemCatId(cid);
        param.setParamData(paramData);
        TaotaoResult taotaoResult = paramService.insertItemParam(param);
        return taotaoResult;
    }
}
