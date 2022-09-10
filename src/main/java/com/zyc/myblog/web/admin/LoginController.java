package com.zyc.myblog.web.admin;

import com.zyc.myblog.dao.UserRepository;
import com.zyc.myblog.po.ImgFlow;
import com.zyc.myblog.po.ImgSide;
import com.zyc.myblog.po.User;
import com.zyc.myblog.service.*;
import com.zyc.myblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class LoginController {


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

    private UserService userService;
    @Autowired
    public void setSysRoleService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginPage(Model model)
    {
        Random r = new Random();
        List<ImgFlow> imgFlows = imgFlowService.findAll();
        int n = (int)Math.ceil(r.nextInt(imgFlows.size()));
        model.addAttribute("backImg",imgFlows.get(n));
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model){
            User user = userService.checkUser(username,password);
             if(user==null){
                 attributes.addFlashAttribute("message","用户名或密码错误");
                 return "redirect:/admin";
             }
             session.setAttribute("user",user);
            /*背景轮播图片 */
             model.addAttribute("imgFlows",imgFlowService.findAll());

            /*侧边的图片*/
            List<ImgSide> list = imgService.findAll();
            String s = "img";
            for (int i = 0; i < list.size(); i++) {
                model.addAttribute(s+i,list.get(i).getUrl());
            }
             return "/admin/index";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        session.removeAttribute("user");
        model.addAttribute("imgFlows",imgFlowService.findAll());
        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "/admin/logoutIndex";
    }



}
