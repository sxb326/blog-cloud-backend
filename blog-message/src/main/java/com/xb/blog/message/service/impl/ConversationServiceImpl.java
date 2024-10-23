package com.xb.blog.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.message.dao.ConversationDao;
import com.xb.blog.message.entity.ConversationEntity;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.vo.ConversationVo;
import com.xb.blog.message.vo.SaveConversationVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationDao, ConversationEntity> implements ConversationService {
    @Override
    public List<ConversationVo> list(String keyword) {
        return baseMapper.list(UserUtil.getUserId(), keyword);
    }

    @Override
    public String checkAndCreate(String sendUserId, String receiveUserId) {
        ConversationEntity entity = baseMapper.selectOne(new QueryWrapper<ConversationEntity>().eq("send_user_id", sendUserId).eq("receive_user_id", receiveUserId));
        if (entity != null) {
            return entity.getId();
        }
        entity = new ConversationEntity();
        entity.setSendUserId(sendUserId);
        entity.setReceiveUserId(receiveUserId);
        save(entity);
        return entity.getId();
    }

    @Override
    public String save(SaveConversationVo vo) {
        ConversationEntity entity = baseMapper.selectOne(new QueryWrapper<ConversationEntity>().eq("send_user_id", UserUtil.getUserId()).eq("receive_user_id", vo.getReceiveUserId()));
        if (entity != null) {
            return entity.getId();
        }
        entity = new ConversationEntity();
        entity.setSendUserId(UserUtil.getUserId());
        entity.setReceiveUserId(vo.getReceiveUserId());
        save(entity);
        return entity.getId();
    }

    @Override
    public void updateNotReceiveCount(String conversationId, int count) {
        baseMapper.updateNotReceiveCount(conversationId, count);
    }

    @Override
    public void clearNotReceiveCount(String conversationId) {
        baseMapper.clearNotReceiveCount(conversationId);
    }
}
