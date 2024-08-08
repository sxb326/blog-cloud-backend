package com.xb.blog.web.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.web.service.CollectService;
import com.xb.blog.web.vo.CollectSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/save")
    public Result save(@RequestBody CollectSaveVo vo) {
        collectService.save(vo);
        return Result.success();
    }
}
