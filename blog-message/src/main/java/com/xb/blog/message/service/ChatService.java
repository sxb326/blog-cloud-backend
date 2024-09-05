package com.xb.blog.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.vo.ContactVo;
import com.xb.blog.message.vo.ContentVo;
import com.xb.blog.message.vo.SendVo;

import java.util.List;

public interface ChatService extends IService<ChatEntity> {
    List<ContentVo> list(String contactUid, Long cursor);

    void send(SendVo vo);
}
