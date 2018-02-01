package cn.hassan.search.controller;

import cn.hassan.search.pojo.SearchResult;
import cn.hassan.search.service.SearchService;
import com.taotao.common.pojo.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String query,@RequestParam(defaultValue = "1")Integer pagr
            ,@RequestParam(defaultValue = "60")Integer rows) {
        if (StringUtils.isBlank(query)) {
            TaotaoResult.build(400, "查询条件不能为空！");
        }else {
            try {
                query = new String(query.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            SearchResult searchResult = searchService.search(query, pagr, rows);
            return TaotaoResult.ok(searchResult);
        }
        return null;
    }
}
