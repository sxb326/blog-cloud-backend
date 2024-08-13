package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.message.service.MessageService;
import com.xb.blog.message.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 查询消息列表
     *
     * @param type
     * @param page
     * @return
     */
    @GetMapping("/list")
    public Result getList(int type, Long page) {
        List<MessageVo> list = messageService.list(type, page);
        return Result.success(list);
    }
}
