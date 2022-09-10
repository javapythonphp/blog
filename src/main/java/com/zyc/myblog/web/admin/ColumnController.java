package com.zyc.myblog.web.admin;

import com.zyc.myblog.po.*;
import com.zyc.myblog.service.*;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ColumnController {
    private BlogService blogService;

    private TypeService typeService;

    private ImgSideService imgService;

    private TagService tagService;

    private ImgFlowService imgFlowService;

    private ColumnService columnService;
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
    @Autowired
    public void setColumnService(ColumnService columnService) {
        this.columnService = columnService;
    }
    /*全局搜索参数 不随刷新改变*/
    String keyWord = "";
    @GetMapping("/columns")
    public String list(@PageableDefault(size=10,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model){

        /*用全局搜索参数返回*/
        Page p = columnService.listColumnByTitle("%"+this.keyWord+"%",pageable);
        model.addAttribute("page",p);

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }

        return "admin/columns";
    }
    /*搜索*/
    @PostMapping("/columns/search")
    public String Search(Model model,@RequestParam(name="keyWord",required=false) String keyWord,
            @PageableDefault(size=10,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable){

            this.keyWord = keyWord;

         Page p = columnService.listColumnByTitle("%"+this.keyWord+"%",pageable);
         model.addAttribute("page",p);

          return "admin/columns :: muban";
    }

    @PostMapping("/columns/flip")
    public String flip(Model model,
                       @PageableDefault(size=10,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable){

        Page p = columnService.listColumnByTitle("%"+this.keyWord+"%",pageable);
        model.addAttribute("page",p);

        return "admin/columns :: muban";
    }

    /*与下面对应,默认均向input页面传输一个Column 防止空指针*/
    @GetMapping("/columns/input")
    public String input(Model model){
        model.addAttribute("column",new Column());
        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/columns-input";
    }


    /*默认添加页面*/
    @PostMapping("/columns")
    public String post(Column column, RedirectAttributes attributes, HttpSession session) throws IOException {
        columnService.saveColumn(column);
        return "redirect:/admin/columns";
    }
    /*通过是否有后缀，确定是修改还是增加 并取得id*/
    @PostMapping("columns/{id}")
    public String editpost(Column column,RedirectAttributes attributes,@PathVariable Long id) throws NotFoundException {
        columnService.updateColumn(id,column);
        return "redirect:/admin/columns";
    }
    /*修改类型的页面*/
    @GetMapping("/columns/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("column",columnService.findColumnById(id));

        /*背景轮播图片 */
        model.addAttribute("imgFlows",imgFlowService.findAll());

        /*侧边的图片*/
        List<ImgSide> list = imgService.findAll();
        String s = "img";
        for (int i = 0; i < list.size(); i++) {
            model.addAttribute(s+i,list.get(i).getUrl());
        }
        return "admin/columns-input";
    }

    @GetMapping("/columns/{id}/delete")
    public String deleteColumn(@PathVariable Long id) throws NotFoundException {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setColumnId(id);
        List<Blog> blogs = blogService.listBlogByColumn(sort,blogQuery);
        for(Blog blog:blogs){
            blog.setColumn(columnService.findColumnById((long)0));
            blogService.updateBlog(blog.getId(),blog);
        }
        columnService.deleteColumn(id);
        return "redirect:/admin/columns";
    }


}
