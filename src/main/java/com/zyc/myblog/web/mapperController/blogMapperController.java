package com.zyc.myblog.web.mapperController;

import com.zyc.myblog.po.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class blogMapperController {

    private com.zyc.myblog.mapperService.blogMapperService blogMapperService;
    @Autowired
    public void setBlogMapperService(com.zyc.myblog.mapperService.blogMapperService blogMapperService) {
        this.blogMapperService = blogMapperService;
    }

    @GetMapping("/AllBlog")
    @ResponseBody
    public List<Blog> findAll(){
        return this.blogMapperService.findAll();
    }

}
