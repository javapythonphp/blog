package com.zyc.myblog.web.admin;


import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.po.Tag;
import com.zyc.myblog.po.Type;
import com.zyc.myblog.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {
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

    @GetMapping("/tags")
    public String list(@PageableDefault(size=99999,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model){
        Page p = tagService.ListTag(pageable);
        model.addAttribute("page",p);

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/tags";
    }
    /*与下面对应,默认均向input页面传输一个type 防止空指针*/
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/tags-input";
    }



    /*默认添加页面*/
    @PostMapping("/tags")
    public String post(Tag t, RedirectAttributes attributes) throws IOException {
        Tag tag = tagService.getTagByName(t.getName());
        if(tag!=null){
            attributes.addFlashAttribute("error","标签名字重复!!!");
            return "redirect:/admin/tags";
        }
        tagService.saveType(t);
        return "redirect:/admin/tags";
    }
    /*通过是否有后缀，确定是修改还是增加 并取得id*/
    @PostMapping("/tags/{id}")
    public String editpost(Tag t,RedirectAttributes attributes,@PathVariable Long id) throws NotFoundException {
        Tag tag = tagService.getTagByName(t.getName());
        if(tag!=null){
            attributes.addFlashAttribute("error","重命名名字重复!!!!!!!!!");
            return "redirect:/admin/tags";
        }
        tagService.updateTag(id,t);
        return "redirect:/admin/tags";
    }
    /*修改类型的页面*/
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getType(id));

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/tags-input";
    }

    @GetMapping("tags/{id}/delete")
    public String deleteType(@PathVariable Long id){
        tagService.deleteType(id);
        return "redirect:/admin/tags";
    }

}
