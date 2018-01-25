package cn.hassan.rest.service.redisImpl;

import cn.hassan.rest.service.JedisClient;
import cn.hassan.rest.service.RedisService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult syncContent(long categoryId) {

        try {
            long hdel = jedisClient.hdel("content", String.valueOf(categoryId));
            return TaotaoResult.ok();
        } catch (Exception e) {
            log.error("清空缓存失败！");
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
