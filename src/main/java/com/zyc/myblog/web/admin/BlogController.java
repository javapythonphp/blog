package com.zyc.myblog.web.admin;

import com.zyc.myblog.po.Blog;
import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.po.User;
import com.zyc.myblog.service.*;
import com.zyc.myblog.util.MarkdownUtils;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private BlogService blogService;

    private TypeService typeService;

    private ImgSideService imgService;

    private TagService tagService;

    private ImgFlowService imgFlowService;

    private ColumnService columnService;
    @Autowired
    public void setColumnService(ColumnService columnService) {
        this.columnService = columnService;
    }
    @Autowired
    public void setImgFlowService(ImgFlowService imgFlowService) { this.imgFlowService = imgFlowService; }
    @Autowired
    public void setTagService(TagService tagService) { this.tagService = tagService; }
    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }
    @Autowired
    public void setImgService(ImgSideService imgService) {
        this.imgService = imgService;
    }
    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size=20,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model){

        Page<Blog> p = blogService.listBlog(pageable,new BlogQuery());
        model.addAttribute("columns",columnService.listColumn());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("page",p);


        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());
        return "admin/blogs";
    }
    private BlogQuery blogQuery = new BlogQuery();
    @PostMapping("/blogs/search")
    public String search(Model m,@PageableDefault(size=20,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog){
        blogQuery.setRecommend(blog.isRecommend());
        blogQuery.setTitle(blog.getTitle());
        blogQuery.setTypeId(blog.getTypeId());
        Page<Blog> p = blogService.listBlog(pageable,blogQuery);
        m.addAttribute("page",p);

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            m.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/blogs :: muban";
    }
    @PostMapping("/blogs/flip")
    public String flip(Model m,@PageableDefault(size=20,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        Page<Blog> p = blogService.listBlog(pageable,blogQuery);

        m.addAttribute("page",p);

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            m.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/blogs :: muban";
    }


    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("columns",columnService.listColumn());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("changeBlog",new Blog());
        model.addAttribute("tags",tagService.listTag());

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/blogs-input";
    }

    @PostMapping("/blogs-sub")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) throws NotFoundException {
         blog.setUser((User) session.getAttribute("user"));
         blog.setType(typeService.getType(blog.getType().getId()));
         blog.setTags(tagService.listTag(blog.getTagIds()));

         blogService.saveBlog(blog);

         return "redirect:/admin/blogs";
    }
public void get(Blog blog){

}
    @GetMapping("/blogs/{id}/input")
    public String change(@PathVariable Long id,Model model){
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("columns",columnService.listColumn());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("changeBlog",b);

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }

        return "admin/blogs-input";
    }

    @PostMapping("/blogs-sub/{id}")
    public String changeAfter(@PathVariable Long id,Blog b) throws NotFoundException {
             b.setUpdateTime(new Date());
             b.setType(typeService.getType(b.getType().getId()));
             b.setTags(tagService.listTag(b.getTagIds()));
             blogService.updateBlog(id,b);

         return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id){
        Blog b = blogService.getBlog(id);
        if(b!=null)
        blogService.deleteBolg(id);
        return "redirect:/admin/blogs";
    }
}
