package com.xb.blog.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.message.dao.ConversationDao;
import com.xb.blog.message.entity.ConversationEntity;
import com.xb.blog.message.service.ConversationService;
import com.xb.blog.message.vo.ConversationVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationDao, ConversationEntity> implements ConversationService {
    @Override
    public List<ConversationVo> list(String keyword) {
        return baseMapper.list(UserUtil.getUserId(), keyword);
    }
}
