package com.xb.blog.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import com.xb.blog.message.config.websocket.server.WebSocketServer;
import com.xb.blog.message.dao.MessageDao;
import com.xb.blog.message.entity.MessageEntity;
import com.xb.blog.message.service.MessageService;
import com.xb.blog.message.vo.MessageCountVo;
import com.xb.blog.message.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 保存消息到消息表，并推送给用户
     *
     * @param dto
     */
    @Override
    public void saveAndSend(MessageDto dto) {
        //保存消息
        String id = dto.getId();
        Boolean exist = baseMapper.exist(id);
        if (!exist) {
            MessageEntity entity = new MessageEntity();
            BeanUtil.copyProperties(dto, entity);
            entity.setIsReceive(false);
            save(entity);

            //推送未读消息条数给用户
            webSocketServer.send(dto.getReceiveUserId());
        }
    }

    /**
     * 根据用户id获取未读消息数量
     *
     * @param id
     * @return
     */
    @Override
    public MessageCountVo getMessageCount(String id) {
        List<Long> counts = baseMapper.getMessageCount(id);
        MessageCountVo vo = new MessageCountVo();
        vo.setTotalCount(counts.stream().mapToLong(Long::longValue).sum());
        vo.setLikeCount(counts.get(0));
        vo.setCommentCount(counts.get(1));
        vo.setCollectCount(counts.get(2));
        vo.setFollowCount(counts.get(3));
        vo.setChatCount(counts.get(4));
        vo.setNoticeCount(counts.get(5));
        return vo;
    }

    /**
     * 获取消息列表
     *
     * @param type
     * @param page
     * @return
     */
    @Override
    public List<MessageVo> list(int type, Long page) {
        if (page == null) page = 1L;
        page = (page - 1L) * 10L;

        String userId = UserUtil.getUserId();
        List<MessageVo> list = baseMapper.list(type, page, userId);
        updateMessageToReceived(type);

        //异步刷新用户的未读消息数
        CompletableFuture.runAsync(() -> webSocketServer.send(userId));

        for (MessageVo vo : list) {
            String sendTimeBefore = vo.getSendTimeBefore();
            long minute = Long.parseLong(sendTimeBefore);
            long year = minute / 525600;
            if (year > 0) {
                vo.setSendTimeBefore(year + "年前");
                continue;
            }
            minute %= 525600;
            long month = minute / 43200;
            if (month > 0) {
                vo.setSendTimeBefore(month + "月前");
                continue;
            }
            month %= 43200;
            long day = minute / 1440;
            if (day > 0) {
                vo.setSendTimeBefore(day + "天前");
                continue;
            }
            minute %= 1440;
            long hour = minute / 60;
            if (hour > 0) {
                vo.setSendTimeBefore(hour + "小时前");
                continue;
            }
            minute %= 60;
            vo.setSendTimeBefore(minute > 0 ? minute + "分钟前" : "刚刚");
        }

        return list;
    }

    /**
     * 获取所有消息未接收的条数
     *
     * @return
     */
    @Override
    public List<Long> counts() {
        return baseMapper.counts(UserUtil.getUserId());
    }

    @Override
    public void updateMessageToReceived(int type) {
        baseMapper.updateMessageToReceived(type, UserUtil.getUserId());
    }
}
