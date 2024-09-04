package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.message.service.ChatService;
import com.xb.blog.message.service.MessageService;
import com.xb.blog.message.vo.ContactVo;
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
    private MessageService messageService;

    @GetMapping("/listContact")
    public Result listContact(String keyword) {
        List<ContactVo> list = chatService.listContact(keyword);
        messageService.updateMessageToReceived(5);
        return Result.success(list);
    }

    @GetMapping("/listContent")
    public Result listContent(String contactUid) {
        List<ContentVo> list = chatService.listContent(contactUid);
        return Result.success(list);
    }

    @PostMapping("/send")
    public Result send(@RequestBody SendVo vo) {
        chatService.send(vo);
        return Result.success();
    }

}
