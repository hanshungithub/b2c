package cn.hassan.rest.controller;

import cn.hassan.rest.pojo.CatResult;
import cn.hassan.rest.service.ItemCatService;
import com.taotao.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


/*    //解决返回的结果集乱码的问题
    @RequestMapping(value = "/itemCat/list",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf8")
    @ResponseBody
    public String getItemCatList(String callback) {
        CatResult itemCatList = itemCatService.getItemCatList();
        String json = JsonUtils.objectToJson(itemCatList);
        //拼装返回js脚本
        String result = callback + "(" + json + ");";
        return result;
    }*/

    @RequestMapping("/itemCat/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        CatResult itemCatList = itemCatService.getItemCatList();
        //springmvc 自带的方法返回
        MappingJacksonValue value = new MappingJacksonValue(itemCatList);
        value.setJsonpFunction(callback);
        return value;
    }
}
