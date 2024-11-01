package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.DraftEntity;
import com.xb.blog.article.vo.ArticleEditorVo;

public interface DraftService extends IService<DraftEntity> {
    /**
     * 保存草稿
     * @param vo
     */
    String saveDraft(ArticleEditorVo vo);
}
