package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.message.service.ChatService;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.vo.ContentVo;
import com.xb.blog.message.vo.SendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/list")
    public Result list(String conversationId, String contactId, Long cursor) {
        List<ContentVo> list = chatService.list(contactId, cursor);
        conversationService.clearNotReceiveCount(conversationId);
        return Result.success(list);
    }

    @PostMapping("/send")
    public Result send(@RequestBody SendVo vo) {
        chatService.send(vo);
        return Result.success();
    }

    @GetMapping("/newest")
    public Result newest(String contactId, Long cursor) {
        List<ContentVo> list = chatService.newest(contactId, cursor);
        return Result.success(list);
    }

}
