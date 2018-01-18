package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;

public interface ContentService {

    EUDataGridResult getContetList(int page, int rows, Long categoryId);
}
