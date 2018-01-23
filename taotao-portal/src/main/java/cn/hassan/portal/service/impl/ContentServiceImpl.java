package cn.hassan.portal.service.impl;

import cn.hassan.portal.service.ContentService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import jdk.nashorn.internal.runtime.JSONFunctions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {
        String response = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        try {
            TaotaoResult result = TaotaoResult.formatToList(response, TbContent.class);
            List<TbContent> data = (List<TbContent>) result.getData();
            List<Map> resultList = new ArrayList<>();
            for (TbContent datum : data) {
                Map map = new HashMap();
                map.put("src", datum.getPic());
                map.put("height", 550);
                map.put("width", 240);
                map.put("srcB", datum.getPic2());
                map.put("height", 240);
                map.put("width", 670);
                map.put("href", datum.getUrl());
                map.put("alt", datum.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            log.error("获取失败");
        }
        return null;
    }
}
