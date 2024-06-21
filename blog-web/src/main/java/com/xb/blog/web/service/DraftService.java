package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.DraftEntity;
import com.xb.blog.web.vo.BlogEditorVo;

public interface DraftService extends IService<DraftEntity> {
    /**
     * 保存草稿
     * @param vo
     */
    String saveDraft(BlogEditorVo vo);
}
