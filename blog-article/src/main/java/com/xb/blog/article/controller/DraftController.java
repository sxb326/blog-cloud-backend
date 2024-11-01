package com.xb.blog.article.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.article.service.DraftService;
import com.xb.blog.article.vo.ArticleEditorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/draft")
public class DraftController {

    @Autowired
    private DraftService draftService;

    /**
     * 保存草稿
     *
     * @param vo
     * @return
     */
    @PostMapping("/save")
    public Result saveDraft(@RequestBody ArticleEditorVo vo) {
        String draftId = draftService.saveDraft(vo);
        return Result.success("保存草稿成功！", draftId);
    }
}
