package cn.hassan.rest.service.impl;

import cn.hassan.rest.service.ContentService;
import cn.hassan.rest.service.JedisClient;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 广告位展示
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(Long contentCid) {

        try {
            String hget = jedisClient.hget("content", String.valueOf(contentCid));
            if(hget != null){
                List<TbContent> result = JsonUtils.jsonToList(hget, TbContent.class);
                return result;
            }
        }catch (Exception e){
            log.error("取缓存失败");
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);

        try {
            String cacheString = JsonUtils.objectToJson(tbContents);
            jedisClient.hset("content", String.valueOf(contentCid), cacheString);
        } catch (Exception e) {
            log.error("天机缓存失败");
        }

        return tbContents;
    }
}
