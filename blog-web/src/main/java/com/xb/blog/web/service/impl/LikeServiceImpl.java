package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.dao.LikeDao;
import com.xb.blog.web.entity.LikeEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.LikeService;
import com.xb.blog.web.vo.LikeSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeDao, LikeEntity> implements LikeService {

    @Autowired
    private BlogService blogService;

    /**
     * 保存点赞行为
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Long save(LikeSaveVo vo) {
        String userId = UserUtil.getUserId();

        //保存点赞表
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

        //如果当前type=1 更新博客点赞数；如果type=2 更新评论点赞数
        int type = vo.getType();
        if (type == 1) {
            return blogService.updateLikeCount(vo.getObjUid(), status ? 1L : -1L);
        } else if (type == 2) {
            //todo 暂不实现
        }
        return 0L;
    }
}
