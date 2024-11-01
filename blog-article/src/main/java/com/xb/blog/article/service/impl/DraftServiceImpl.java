package com.xb.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.article.dao.DraftDao;
import com.xb.blog.article.entity.DraftEntity;
import com.xb.blog.article.service.ArticleTagService;
import com.xb.blog.article.service.DraftService;
import com.xb.blog.article.vo.ArticleEditorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DraftServiceImpl extends ServiceImpl<DraftDao, DraftEntity> implements DraftService {

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 保存草稿
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveDraft(ArticleEditorVo vo) {

        //保存草稿主体数据
        DraftEntity entity = new DraftEntity();
        BeanUtil.copyProperties(vo, entity);
        entity.setAuthor(UserUtil.getUserId());
        saveOrUpdate(entity);

        //保存标签绑定数据
        articleTagService.save(vo.getId(), vo.getTagIds());

        return entity.getId();
    }
}
