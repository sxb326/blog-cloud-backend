package com.xb.blog.search.vo;

import com.xb.blog.common.pojo.BlogDocument;
import lombok.Data;

import java.util.List;

@Data
public class SearchVo {
    /**
     * 检索总条数
     */
    private Long total;
    /**
     * 数据集合
     */
    List<BlogDocument> list;
}
