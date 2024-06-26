package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.dao.LikeDao;
import com.xb.blog.web.entity.LikeEntity;
import com.xb.blog.web.service.LikeService;
import com.xb.blog.web.vo.LikeSaveVo;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeDao, LikeEntity> implements LikeService {
    /**
     * 保存点赞行为
     *
     * @param vo
     */
    @Override
    public void save(LikeSaveVo vo) {
        String userId = UserUtil.getUserId();

        Boolean status = vo.getStatus();
        if (status) {
            LikeEntity entity = new LikeEntity();
            BeanUtil.copyProperties(vo, entity);
            entity.setUserUid(userId);
            save(entity);
        } else {
            QueryWrapper<LikeEntity> wrapper = new QueryWrapper<LikeEntity>()
                    .eq("type", vo.getType())
                    .eq("obj_uid", vo.getObjUid())
                    .eq("user_uid", userId);
            remove(wrapper);
        }
    }
}
