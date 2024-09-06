package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.service.MessageService;
import com.xb.blog.message.vo.ConversationVo;
import com.xb.blog.message.vo.SaveConversationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    /**
     * 查询出用户当前所有会话
     *
     * @param keyword
     * @return
     */
    @GetMapping("/list")
    public Result list(String keyword) {
        List<ConversationVo> list = conversationService.list(keyword);
        messageService.updateMessageToReceived(5);
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result save(@RequestBody SaveConversationVo vo) {
        String conversationUid = conversationService.save(vo);
        return Result.success("", conversationUid);
    }

}
