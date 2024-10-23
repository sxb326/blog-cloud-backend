package com.xb.blog.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.message.entity.ConversationEntity;
import com.xb.blog.message.vo.ConversationVo;
import com.xb.blog.message.vo.SaveConversationVo;

import java.util.List;

public interface ConversationService extends IService<ConversationEntity> {
    List<ConversationVo> list(String keyword);

    String checkAndCreate(String sendUserId, String receiveUserId);

    String save(SaveConversationVo vo);

    void updateNotReceiveCount(String conversationId, int count);

    void clearNotReceiveCount(String conversationId);
}
