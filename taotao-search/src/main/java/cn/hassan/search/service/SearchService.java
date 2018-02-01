package cn.hassan.search.service;

import cn.hassan.search.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String query, int page, int rows);
}
