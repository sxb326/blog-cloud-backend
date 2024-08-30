package com.xb.blog.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.vo.ContactVo;

import java.util.List;

public interface ChatService extends IService<ChatEntity> {
    List<ContactVo> listContact(String keyword, Long page);
}
