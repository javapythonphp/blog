package com.zyc.myblog.web.admin;

import com.zyc.myblog.po.ImgFlow;
import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.service.*;
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
public class ImgFlowController {
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
    @GetMapping("/imgFlows")
    public String goImgFlow(Model e){
        List<ImgFlow> list = imgFlowService.findAll();
        e.addAttribute("imgFlows",list);


        /*侧边的图片*/
        List<ImgSide> list1 = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list1.size(); i++) {
            e.addAttribute(s+i,list1.get(i).getUrl());
        }
        return "admin/imgFlows";
    }
    @GetMapping("/imgFlows/input")
    public String imgFlowsInput(Model e){
        e.addAttribute("imgFlow",new ImgFlow());

        List<ImgFlow> list = imgFlowService.findAll();
        e.addAttribute("imgFlows",list);

        /*侧边的图片*/
        List<ImgSide> list1 = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list1.size(); i++) {
            e.addAttribute(s+i,list1.get(i).getUrl());
        }
        return "admin/imgFlows-input";
    }
    @PostMapping("/imgFlows-input")
    public String putImgFlow(ImgFlow imgFlow,RedirectAttributes attributes){
         ImgFlow img = imgFlowService.saveImg(imgFlow);
         if(img==null){
             attributes.addFlashAttribute("error","新增失败");
         }else{
             attributes.addFlashAttribute("error","新增成功");
         }
         return "redirect:/admin/imgFlows";
    }

    @GetMapping("/imgFlows/{id}/input")
    public String goImgFlow1(@PathVariable  Long id, Model e,RedirectAttributes attributes){
         ImgFlow imgFlow = imgFlowService.findById(id);
         if(imgFlow==null){
             attributes.addFlashAttribute("error","该图片不存在!");
             return "redirect:/admin/imgFlows";
         }
        List<ImgFlow> list = imgFlowService.findAll();
        e.addAttribute("imgFlows",list);

         e.addAttribute("imgFlow",imgFlow);

        /*侧边的图片*/
        /*侧边的图片*/
        List<ImgSide> list1 = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list1.size(); i++) {
            e.addAttribute(s+i,list1.get(i).getUrl());
        }
         return "admin/imgFlows-input";
    }
    @PostMapping("/imgFlows-input/{id}")
    public String updateImgFlow(@PathVariable  Long id, ImgFlow imgFlow, Model e,RedirectAttributes attributes){
        ImgFlow img = imgFlowService.findById(id);
        if(img==null){
            imgFlowService.saveImg(imgFlow);
        }
        ImgFlow img1 = imgFlowService.updateImg(id,imgFlow);
        if(img1==null){
            attributes.addFlashAttribute("error","修改失败!");
        }else{
            attributes.addFlashAttribute("error","修改成功");
        }
         return "redirect:/admin/imgFlows";
    }

    @GetMapping("/imgFlows/{id}/delete")
    public String deleteImgFlow(@PathVariable  Long id){
        imgFlowService.deleteImgById(id);

        return "redirect:/admin/imgFlows";
    }




}
