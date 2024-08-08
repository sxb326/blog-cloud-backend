package com.xb.blog.web.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.web.service.DraftService;
import com.xb.blog.web.vo.BlogEditorVo;
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
    public Result saveDraft(@RequestBody BlogEditorVo vo) {
        String draftUid = draftService.saveDraft(vo);
        return Result.success("保存草稿成功！", draftUid);
    }
}
