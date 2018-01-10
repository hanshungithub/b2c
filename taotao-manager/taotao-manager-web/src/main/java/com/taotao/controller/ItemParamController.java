package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
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
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatId) {
        TaotaoResult itemParamByCid = paramService.getItemParamByCid(itemCatId);
        return itemParamByCid;
    }
}
