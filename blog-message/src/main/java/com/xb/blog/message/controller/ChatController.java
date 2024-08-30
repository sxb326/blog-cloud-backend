package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.message.service.ChatService;
import com.xb.blog.message.vo.ContactVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/listContact")
    public Result listContact(String keyword, Long page) {
        List<ContactVo> list = chatService.listContact(keyword, page);
        return Result.success(list);
    }

}
