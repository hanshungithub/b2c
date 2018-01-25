package cn.hassan.rest.controller;

import cn.hassan.rest.service.RedisService;
import com.taotao.common.pojo.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/cache/sync")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{categoryId}")
    @ResponseBody
    public TaotaoResult syncContent(@PathVariable long categoryId) {
        return redisService.syncContent(categoryId);
    }
}
