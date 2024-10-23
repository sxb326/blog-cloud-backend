package com.xb.blog.message.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.message.dao.ChatDao;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.publisher.MessagePublisher;
import com.xb.blog.message.service.ChatService;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.vo.ContentVo;
import com.xb.blog.message.vo.SendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatDao, ChatEntity> implements ChatService {

    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    private ConversationService conversationService;

    @Override
    public List<ContentVo> list(String contactId, Long cursor) {
        List<ContentVo> list = baseMapper.list(UserUtil.getUserId(), contactId, cursor);
        CollUtil.reverse(list);
        return list;
    }

    @Override
    public void send(SendVo vo) {
        //保存消息
        ChatEntity entity = new ChatEntity();
        entity.setSendUserId(UserUtil.getUserId());
        entity.setReceiveUserId(vo.getContactId());
        entity.setIsReceive(false);
        entity.setContent(vo.getContent());
        save(entity);

        //检查对方是否有对应会话 如果没有则创建
        String conversationId = conversationService.checkAndCreate(vo.getContactId(), UserUtil.getUserId());

        //更新对方会话的未读数
        conversationService.updateNotReceiveCount(conversationId, 1);

        //发送消息
        if (StrUtil.isNotBlank(vo.getContent())) {
            messagePublisher.sendMessage(5, "", UserUtil.getUserId(), vo.getContactId(), "", "");
        }
    }

    @Override
    public List<ContentVo> newest(String contactId, Long cursor) {
        return baseMapper.newest(UserUtil.getUserId(), contactId, cursor);
    }
}
