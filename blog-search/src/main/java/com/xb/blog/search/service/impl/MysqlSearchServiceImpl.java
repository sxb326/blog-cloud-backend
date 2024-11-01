package com.xb.blog.search.service.impl;

import cn.hutool.json.JSONUtil;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.search.feign.BlogFeignService;
import com.xb.blog.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 从Mysql检索
 */
@Service("mysql")
public class MysqlSearchServiceImpl implements SearchService {

    @Autowired
    private BlogFeignService blogFeignService;

    /**
     * 根据传入的关键字以及分页参数，检索数据 封装为SearchVo并返回
     *
     * @param keyword
     * @param page
     * @return
     */
    @Override
    public SearchVo search(String keyword, Long page) {
        Result result = blogFeignService.search(keyword, page);
        Object data = result.getData();
        if (data != null) {
            return JSONUtil.toBean(JSONUtil.toJsonStr(data), SearchVo.class);
        }
        return null;
    }

    /**
     * 在返回数据前 进行的额外操作
     * 高亮关键字
     *
     * @param vo
     * @return
     */
    @Override
    public void beforeReturn(SearchVo vo) {
        String keyword = vo.getKeyword();
        for (ArticleDocument doc : vo.getList()) {
            String title = doc.getTitle();
            doc.setTitle(title.replace(keyword, "<span style='color:red'>" + keyword + "</span>"));
        }
    }
}
