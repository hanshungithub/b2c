package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
    List<EUTreeNode> getCategoryList(Long parentId);

    //添加节点
    TaotaoResult insertContentCategory(Long parentId, String name);

    //删除节点
    TaotaoResult deleteContentCategory(Long parentId, Long id);

    //更新节点
    TaotaoResult updateContentCategory(Long id, String name);
}
