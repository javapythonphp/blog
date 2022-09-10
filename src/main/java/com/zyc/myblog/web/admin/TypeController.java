package com.zyc.myblog.web.admin;

import com.zyc.myblog.po.Blog;
import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.po.Type;
import com.zyc.myblog.service.*;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class    TypeController {

    private BlogService blogService;

    private TypeService typeService;

    private ImgSideService imgService;

    private TagService tagService;

    private ImgFlowService imgFlowService;
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

    @GetMapping("/types")
    public String list(@PageableDefault(size=9999,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
                                       , Model model){
        Page p = typeService.ListType(pageable);
        model.addAttribute("page",p);

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/types";
    }
    /*与下面对应,默认均向input页面传输一个type 防止空指针*/
    @GetMapping("/types/input")
    public String input(Model model){
        /*暂时不对外开放*/
       /* model.addAttribute("type",new Type());*/

        /*背景轮播图片 */
      /*  model.addAttribute("imgFlows",imgFlowService.findAll());*/

        /*侧边的图片*/
     /*   List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }*/
        return "admin/types-input";
    }



    /*默认添加页面*/
    @PostMapping("/types")
    public String post(Type t,RedirectAttributes attributes) throws IOException {
        Type type = typeService.findByName(t.getName());
        if(type!=null){
            attributes.addFlashAttribute("error","分类名字重复!!!");
            return "redirect:/admin/types";
        }
        typeService.saveType(t);
        return "redirect:/admin/types";
    }
    /*通过是否有后缀，确定是修改还是增加 并取得id*/
    @PostMapping("/types/{id}")
    public String editpost(Type t,RedirectAttributes attributes,@PathVariable Long id) throws NotFoundException {
        Type type = typeService.findByName(t.getName());
        if(type!=null){
            attributes.addFlashAttribute("error","重命名名字重复!!!!!!!!!");
            return "redirect:/admin/types";
        }
        typeService.updateType(id,t);
        return "redirect:/admin/types";
    }
/*修改类型的页面*/
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id) throws NotFoundException {
      /*   BlogQuery blogQuery = new BlogQuery();
         blogQuery.setTypeId(id);
         Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
         List<Blog> blogs = blogService.listBlogByType(sort,blogQuery);
         for(Blog blog:blogs){
             blog.setType(typeService.getType((long)0));
             blogService.updateBlog(blog.getId(),blog);
         }
         typeService.deleteType(id);*/
         return "redirect:/admin/types";
    }

}
