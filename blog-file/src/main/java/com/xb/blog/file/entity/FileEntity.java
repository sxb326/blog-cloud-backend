package com.xb.blog.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_file")
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件后缀 用于区分文件类型
     */
    private String suffix;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 逻辑删除 1：不删除，0：删除
     */
    @TableLogic(value = "1", delval = "0")
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
