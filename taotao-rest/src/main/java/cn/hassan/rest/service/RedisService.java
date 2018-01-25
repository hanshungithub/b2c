package cn.hassan.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface RedisService {

    TaotaoResult syncContent(long categoryId);
}
