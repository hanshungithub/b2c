package cn.hassan.rest.service.impl;

import cn.hassan.rest.service.ContentService;
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

    /**
     * 广告位展示
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(Long contentCid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);
        return tbContents;
    }
}
