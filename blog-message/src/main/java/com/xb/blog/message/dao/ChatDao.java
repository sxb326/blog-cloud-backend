package com.xb.blog.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.vo.ContactVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatDao extends BaseMapper<ChatEntity> {
    /**
     * 查询登录用户对应的联系人列表
     *
     * @param userUid 当前登录用户
     * @param keyword 关键字
     * @param page    分页
     * @return
     */
    List<ContactVo> listContact(@Param("userUid") String userUid, @Param("keyword") String keyword, @Param("page") Long page);
}