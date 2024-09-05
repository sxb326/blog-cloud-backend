package com.xb.blog.message.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.message.dao.ChatDao;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.publisher.MessagePublisher;
import com.xb.blog.message.service.ChatService;
import com.xb.blog.message.vo.ContentVo;
import com.xb.blog.message.vo.SendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatDao, ChatEntity> implements ChatService {

    @Autowired
    private MessagePublisher messagePublisher;

    @Override
    public List<ContentVo> list(String contactUid, Long cursor) {
        List<ContentVo> list = baseMapper.list(UserUtil.getUserId(), contactUid, cursor);
        CollUtil.reverse(list);
        return list;
    }

    @Override
    public void send(SendVo vo) {
        ChatEntity entity = new ChatEntity();
        entity.setSendUserUid(UserUtil.getUserId());
        entity.setReceiveUserUid(vo.getContactUid());
        entity.setIsReceive(false);
        entity.setContent(vo.getContent());
        save(entity);

        //发送消息
        if (StrUtil.isNotBlank(vo.getContent())) {
            messagePublisher.sendMessage(5, "", UserUtil.getUserId(), vo.getContactUid(), "", "");
        }
    }
}
