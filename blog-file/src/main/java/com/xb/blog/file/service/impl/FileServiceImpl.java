package com.xb.blog.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.file.dao.FileDao;
import com.xb.blog.file.entity.FileEntity;
import com.xb.blog.file.service.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {
}
