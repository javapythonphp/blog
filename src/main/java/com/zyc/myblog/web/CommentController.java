package com.zyc.myblog.web;

import com.alibaba.fastjson.JSON;
import com.zyc.myblog.po.Comment;
import com.zyc.myblog.service.BlogService;
import com.zyc.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zyc.myblog.util.getMusic;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class CommentController {

    private String head = "https://tenapi.cn/bilibili/?uid=";

    private CommentService commentService;

    private BlogService blogService;
    private String avatar;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model, HttpSession session){
        List<Comment> l = commentService.listCommentByBlogId(blogId);

        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));

        model.addAttribute("user",session.getAttribute("user"));
        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, RedirectAttributes attributes){
        Long blogid=  comment.getBlog().getId();

        String s = getMusic.loadJson(head+comment.getUid());
        Map<String,Object> jo = (Map) JSON.parse(s);
        if((int)jo.get("code")==0){
            Map<String,String> re = (Map<String,String>)jo.get("data");
            comment.setUserpage("https://space.bilibili.com/"+re.get("uid"));
            comment.setNickname(re.get("name"));
            comment.setAvatar(re.get("avatar"));
            comment.setBlog(blogService.getBlog(blogid));
            commentService.saveComment(comment);
        }else{
            attributes.addFlashAttribute("error","该用户不存在");
        }
        return "redirect:/blog/comments/"+comment.getBlog().getId();
    }

    @PostMapping("/comments/delete")
    public String delete(Model model,Comment comment, RedirectAttributes attributes,HttpSession session){
        test(comment);

        Long Id = comment.getId();
        Long blogId = comment.getBlog().getId();

        commentService.deleteComment(Id);

        List<Comment> l = commentService.listCommentByBlogId(blogId);

        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));

        model.addAttribute("user",session.getAttribute("user"));
        return "redirect:/blog/comments/"+blogId;
    }
    public void test(Comment comment){

    }

    @GetMapping("/css")
    public String cs(Model m){



        return "/cs";
    }
}
