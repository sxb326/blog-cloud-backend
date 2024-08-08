package com.xb.blog.search.service;


import com.xb.blog.common.core.vo.SearchVo;

/**
 * 检索
 */
public interface SearchService {
    /**
     * 根据传入的关键字以及分页参数，检索数据 封装为SearchVo并返回
     *
     * @param keyword
     * @param page
     * @return
     */
    SearchVo search(String keyword, Long page);

    /**
     * 在返回数据前 进行的额外操作 例如：当使用mysql查询时 需要额外进行高亮关键字的处理
     *
     * @param vo
     * @return
     */
    default void beforeReturn(SearchVo vo) {
    }
}
