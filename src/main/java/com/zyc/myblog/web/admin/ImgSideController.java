package com.zyc.myblog.web.admin;

import com.zyc.myblog.po.ImgFlow;
import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ImgSideController {
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
    @GetMapping("/imgSides")
    public String goImgSides(Model e){
        List<ImgSide> list = imgService.findAll();
        e.addAttribute("imgSides",list);

        /*背景轮播图片 */
        e.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list1 = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list1.size(); i++) {
            e.addAttribute(s+i,list1.get(i).getUrl());
        }
        return "admin/imgSides";
    }
    @GetMapping("/imgSides/input")
    public String imgSidesInput(Model e){
        e.addAttribute("imgSides",new ImgSide());

        /*背景轮播图片 */
        e.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list1 = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list1.size(); i++) {
            e.addAttribute(s+i,list1.get(i).getUrl());
        }
        return "admin/imgSides-input";
    }
    @PostMapping("/imgSides-input")
    public String putimgSides(ImgSide imgSide, RedirectAttributes attributes){
        ImgSide img = imgService.saveImg(imgSide);
        if(img==null){
            attributes.addFlashAttribute("error","新增失败");
        }else{
            attributes.addFlashAttribute("error","新增成功");
        }
        return "redirect:/admin/imgSides";
    }

    @GetMapping("/imgSides/{id}/input")
    public String goimgSides1(@PathVariable Long id, Model e, RedirectAttributes attributes) throws NotFoundException {
        ImgSide imgSide = imgService.findById(id);
        if(imgSide==null){
            attributes.addFlashAttribute("error","该图片不存在!");
            return "redirect:/admin/imgSides";
        }
        e.addAttribute("imgSides",imgSide);
        /*背景轮播图片 */
        e.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list1 = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list1.size(); i++) {
            e.addAttribute(s+i,list1.get(i).getUrl());
        }
        return "admin/imgSides-input";
    }
    @PostMapping("/imgSides-input/{id}")
    public String updateimgSides(@PathVariable  Long id, ImgSide imgSide, Model e,RedirectAttributes attributes) throws NotFoundException {
        ImgSide imgSide1 = imgService.findById(id);
        if(imgSide1==null){
            throw new NotFoundException("无法更新,id不存在");
        }
        ImgSide imgSide2 = imgService.updateImg(id,imgSide);
        if(imgSide2==null){
            attributes.addFlashAttribute("error","修改失败!");
        }else{
            attributes.addFlashAttribute("error","修改成功");
        }
        return "redirect:/admin/imgSides";
    }

    @GetMapping("/imgSides/{id}/delete")
    public String deleteImgSides(@PathVariable  Long id){
        imgService.deleteImgById(id);

        return "redirect:/admin/imgSides";
    }




}
