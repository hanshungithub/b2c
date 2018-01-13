package com.taotao.controller;

import com.taotao.service.ItemParamItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService paramItemService;

    @RequestMapping("/show/item/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model) {
        String result = paramItemService.getItemParamByItemId(itemId);
        model.addAttribute("itemParam", result);
        return "item";
    }
}
