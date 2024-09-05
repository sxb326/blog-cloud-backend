package com.xb.blog.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.message.entity.ConversationEntity;
import com.xb.blog.message.vo.ConversationVo;

import java.util.List;

public interface ConversationService extends IService<ConversationEntity> {
    List<ConversationVo> list(String keyword);
}
