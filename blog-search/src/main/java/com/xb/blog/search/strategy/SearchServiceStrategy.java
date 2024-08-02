package com.xb.blog.search.strategy;

import com.xb.blog.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 检索Service策略类
 */
@Service
public class SearchServiceStrategy {

    @Autowired
    private final Map<String, SearchService> map = new HashMap<>();

    @Autowired
    @Qualifier("mysql")
    private SearchService DEFAULT_SEARCH_SERVICE;

    /**
     * 根据传入type，获取对应service
     *
     * @param type
     * @return
     */
    public SearchService getService(String type) {
        SearchService service = map.get(type);
        if (service != null) {
            return service;
        }
        return DEFAULT_SEARCH_SERVICE;
    }
}
