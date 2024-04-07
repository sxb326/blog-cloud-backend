package com.xb.blog.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 博客标签关联表
 * 
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Data
@TableName("t_blog_tag")
public class BlogTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String uid;
	/**
	 * 博客uid
	 */
	private String blogUid;
	/**
	 * 标签uid
	 */
	private String tagUid;
	/**
	 * 逻辑删除 1：不删除，0：删除
	 */
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
