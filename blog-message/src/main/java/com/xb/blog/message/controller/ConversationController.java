package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.vo.ContactVo;
import com.xb.blog.message.vo.ConversationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    /**
     * 查询出用户当前所有会话
     *
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    public Result list(String keyword) {
        List<ConversationVo> list = conversationService.list(keyword);
        return Result.success(list);
    }

}
