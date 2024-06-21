package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.dao.DraftDao;
import com.xb.blog.web.entity.DraftEntity;
import com.xb.blog.web.service.DraftService;
import com.xb.blog.web.vo.BlogEditorVo;
import org.springframework.stereotype.Service;

@Service
public class DraftServiceImpl extends ServiceImpl<DraftDao, DraftEntity> implements DraftService {
    /**
     * 保存草稿
     *
     * @param vo
     */
    @Override
    public String saveDraft(BlogEditorVo vo) {
        DraftEntity entity = new DraftEntity();
        BeanUtil.copyProperties(vo, entity);
        saveOrUpdate(entity);
        return entity.getUid();
    }
}
