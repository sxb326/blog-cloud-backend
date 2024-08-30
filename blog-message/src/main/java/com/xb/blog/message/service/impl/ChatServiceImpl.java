package com.xb.blog.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.message.dao.ChatDao;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.service.ChatService;
import com.xb.blog.message.vo.ContactVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatDao, ChatEntity> implements ChatService {
    @Override
    public List<ContactVo> listContact(String keyword, Long page) {
        if (page == null) page = 1L;
        page = (page - 1L) * 10L;
        String userUid = UserUtil.getUserId();
        return baseMapper.listContact(userUid, keyword, page);
    }
}
