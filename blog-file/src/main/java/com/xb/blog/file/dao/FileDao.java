package com.xb.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.file.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao extends BaseMapper<FileEntity> {
}
