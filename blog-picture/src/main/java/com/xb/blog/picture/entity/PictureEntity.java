package com.xb.blog.picture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_picture")
public class PictureEntity implements Serializable {
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
     * 存储方式：local本地存储 os对象存储
     */
    private String storageType;
    /**
     * 本地存储时 图片访问路径
     */
    private String localPath;
    /**
     * 对象存储时 bucket名称
     */
    private String osBucket;
    /**
     * 对象存储时 图片的唯一key
     */
    private String osKey;
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
