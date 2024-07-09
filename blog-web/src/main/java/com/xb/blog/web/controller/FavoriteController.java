package com.xb.blog.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.service.FavoriteService;
import com.xb.blog.web.vo.FavoriteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收藏夹
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 获取用户的收藏夹列表
     *
     * @return
     */
    @GetMapping("/list/{id}")
    public Result list(@PathVariable("id") String blogId) {
        //查询用户的收藏夹列表
        List<FavoriteVo> list = favoriteService.listFavorite(blogId);
        if (CollUtil.isEmpty(list)) {
            //创建默认收藏夹
            favoriteService.createDefaultFavorite();
            list = favoriteService.listFavorite(blogId);
        }
        return Result.success(list);
    }
}
