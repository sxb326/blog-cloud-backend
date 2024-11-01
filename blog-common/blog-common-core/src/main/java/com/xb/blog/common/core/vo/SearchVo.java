package com.xb.blog.common.core.vo;

import com.xb.blog.common.core.pojo.ArticleDocument;
import lombok.Data;

import java.util.List;

@Data
public class SearchVo {
    /**
     * 检索关键字
     */
    private String keyword;
    /**
     * 检索总条数
     */
    private Long total;
    /**
     * 数据集合
     */
    List<ArticleDocument> list;
}
