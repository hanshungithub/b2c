package cn.hassan.rest.controller;

import cn.hassan.rest.service.ContentService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long contentCategoryId) {
        try {
            List<TbContent> contentList = contentService.getContentList(contentCategoryId);
            return TaotaoResult.ok(contentList);
        } catch (Exception e) {
            log.error("查询失败-->{}" + e);
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
