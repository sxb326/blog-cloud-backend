package com.xb.blog.message.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.message.config.websocket.server.WebSocketServer;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.service.MessageService;
import com.xb.blog.message.vo.ConversationVo;
import com.xb.blog.message.vo.SaveConversationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private WebSocketServer webSocketServer;

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

        //异步刷新用户的未读消息数
        String userId = UserUtil.getUserId();
        CompletableFuture.runAsync(() -> webSocketServer.send(userId));

        return Result.success(list);
    }

    @PostMapping("/save")
    public Result save(@RequestBody SaveConversationVo vo) {
        String conversationId = conversationService.save(vo);
        return Result.success("", conversationId);
    }

}
