package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.web.dao.FollowDao;
import com.xb.blog.web.entity.FollowEntity;
import com.xb.blog.web.service.FollowService;
import com.xb.blog.web.vo.FollowSaveVo;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowDao, FollowEntity> implements FollowService {
    @Override
    public void save(FollowSaveVo vo) {
        Boolean isFollow = vo.getIsFollow();
        if (isFollow) {
            FollowEntity entity = new FollowEntity();
            entity.setUserUid(UserUtil.getUserId());
            entity.setTargetUserUid(vo.getTargetUserUid());
            save(entity);
        } else {
            remove(new QueryWrapper<FollowEntity>().eq("user_uid", UserUtil.getUserId()).eq("target_user_uid", vo.getTargetUserUid()));
        }
    }
}
