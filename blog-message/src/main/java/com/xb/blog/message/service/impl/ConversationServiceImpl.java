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
    public String checkAndCreate(String sendUserUid, String receiveUserUid) {
        ConversationEntity entity = baseMapper.selectOne(new QueryWrapper<ConversationEntity>().eq("send_user_uid", sendUserUid).eq("receive_user_uid", receiveUserUid));
        if (entity != null) {
            return entity.getUid();
        }
        entity = new ConversationEntity();
        entity.setSendUserUid(sendUserUid);
        entity.setReceiveUserUid(receiveUserUid);
        save(entity);
        return entity.getUid();
    }

    @Override
    public String save(SaveConversationVo vo) {
        ConversationEntity entity = baseMapper.selectOne(new QueryWrapper<ConversationEntity>().eq("send_user_uid", UserUtil.getUserId()).eq("receive_user_uid", vo.getReceiveUserUid()));
        if (entity != null) {
            return entity.getUid();
        }
        entity = new ConversationEntity();
        entity.setSendUserUid(UserUtil.getUserId());
        entity.setReceiveUserUid(vo.getReceiveUserUid());
        save(entity);
        return entity.getUid();
    }

    @Override
    public void updateNotReceiveCount(String conversationUid, int count) {
        baseMapper.updateNotReceiveCount(conversationUid, count);
    }

    @Override
    public void clearNotReceiveCount(String conversationUid) {
        baseMapper.clearNotReceiveCount(conversationUid);
    }
}
