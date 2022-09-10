package com.zyc.myblog.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zyc.myblog.po.*;
import com.zyc.myblog.service.*;
import com.zyc.myblog.util.getMusic;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {

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





    /*主页*/
    /*column查询变量*/
    private String columnTitle = "";
    @GetMapping("/index")
    public String goIndex(Model m, @PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){
        /*歌曲列表*/
        addMusicList(m);
        /*侧边的图片*/
        addRightImgList(m);
        /*pixiv日榜*/
        addPixivList(m);
        /*加载页面随机图片*/
        randomImgLoad(m);

        /*博客列表*/
        if(this.que!=null&&!this.que.equals("")){
            m.addAttribute("page",blogService.listBlog("%"+this.que+"%",pageable));

        }else{
            m.addAttribute("page",blogService.listBlog(pageable));
        }

        /*专栏详细列表*/
        m.addAttribute("columnsDetails",columnService.findAllByTitle("%"+this.columnTitle+"%"));
        /*专栏列表*/
        m.addAttribute("columns",columnService.listTop(20));
        /*分类排行列表*/
        m.addAttribute("types",typeService.listTypeTop(100000));
        /*标签排行列表*/
        m.addAttribute("tags",tagService.listTagTop(100000));
        /*推荐博客列表*/
        m.addAttribute("recommend",blogService.listRecommendBlogTop(20));
        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        /*右边分类详情*/
        right(m);

        return "index";
    }
    /*主页翻页*/
    @PostMapping("/index/flip")
    public String goFlip(Model m, @PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){

        if(this.que!=null&&!que.equals("")){
            m.addAttribute("page",blogService.listBlog("%"+this.que+"%",pageable));
        }else {
            m.addAttribute("page", blogService.listBlog(pageable));
        }
        return "index::BlogPage";

    }

    /*全局搜索变量*/
    private String que = "";
    @PostMapping("/index/search")
    public String search(Model m, @RequestParam(name="query") String query, @PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){
        /*把参数放入新页面搜索框*/
        this.que = query;
        return "redirect:/index";
    }



/*通过type查询*/
    /*type查询变量*/
    BlogQuery blogQueryType = new BlogQuery();
    @GetMapping("/index/types/{id}")
    public String toTypes(@PageableDefault(size=10,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model m, @PathVariable Long id){
        List<Type> types = typeService.listTypeTop(1000000);
        blogQueryType.setTypeId(id);
        /*暂存当前type的id,显示分类名字*/
        m.addAttribute("typeId",blogQueryType.getTypeId());
        m.addAttribute("page",blogService.listBlog(pageable,blogQueryType));

        /*歌曲列表*/
        addMusicList(m);
        /*侧边的图片*/
        addRightImgList(m);
        /*pixiv日榜*/
        addPixivList(m);
        /*加载页面随机图片*/
        randomImgLoad(m);


        /*专栏详细列表*/
        m.addAttribute("columnsDetails",columnService.findAllByTitle("%"+this.columnTitle+"%"));
        /*专栏列表*/
        m.addAttribute("columns",columnService.listTop(20));
        /*分类排行列表*/
        m.addAttribute("types",typeService.listTypeTop(10000));
        /*标签排行列表*/
        m.addAttribute("tags",tagService.listTagTop(10000));
        /*推荐博客列表*/
        m.addAttribute("recommend",blogService.listRecommendBlogTop(20));
        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        /*右边分类详情*/
        right(m);

        return "index-type";
    }

    /*type分类后翻页*/
    @PostMapping("/index/types/flip")
    public String goTypeFlip(Model m, @PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){

        m.addAttribute("page",blogService.listBlog(pageable,blogQueryType));

        return "index-type::BlogPage";

    }



 /*通过tag查询*/
    /*tag变量*/
    private Long tagId;
    @GetMapping("/index/tags/{id}")
    public String toTags(@PageableDefault(size=10,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model m, @PathVariable Long id){
        List<Tag> tag = tagService.listTagTop(1000000);
        this.tagId = id;
        m.addAttribute("page",blogService.listBlog(tagId,pageable));
        /*暂存当前type的id,显示分类名字*/
        m.addAttribute("tagId",tagId);
        /*pixiv日榜*/
        addPixivList(m);
        /*歌曲列表*/
        addMusicList(m);
        /*侧边的图片*/
        addRightImgList(m);
        /*加载页面随机图片*/
        randomImgLoad(m);

        List<Column> columns = columnService.listTop(20);

        /*专栏详细列表*/
        m.addAttribute("columnsDetails",columnService.findAllByTitle("%"+this.columnTitle+"%"));
        /*专栏列表*/
        m.addAttribute("columns",columnService.listTop(20));
        /*分类排行列表*/
        m.addAttribute("types",typeService.listTypeTop(10000));
        /*标签排行列表*/
        m.addAttribute("tags",tagService.listTagTop(10000));
        /*推荐博客列表*/
        m.addAttribute("recommend",blogService.listRecommendBlogTop(20));
        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        /*右边分类详情*/
        right(m);

        return "index-tag";
    }

    /*tag分类后翻页*/
    @PostMapping("/index/tags/flip")
    public String goTagFlip(Model m, @PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){

        m.addAttribute("page",blogService.listBlog(tagId,pageable));

        return "index-tag::BlogPage";
    }

    /*通过column查询*/
    /*column页面查询变量*/
    BlogQuery blogQueryColumn = new BlogQuery();
    @GetMapping("/index/columns/{id}")
    public String toColumns(@PageableDefault(size=10,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model m, @PathVariable Long id){
        blogQueryColumn.setColumnId(id);
        /*暂存当前type的id,显示分类名字*/
        m.addAttribute("ColumnId",id);
        m.addAttribute("page",blogService.listBlog(pageable,blogQueryColumn));

        /*歌曲列表*/
        addMusicList(m);
        /*侧边的图片*/
        addRightImgList(m);
        /*pixiv日榜*/
        addPixivList(m);
        /*加载页面随机图片*/
        randomImgLoad(m);

        /*所有专栏*/
        m.addAttribute("allColumns",columnService.listColumn());
        /*专栏详细列表*/
        m.addAttribute("columnsDetails",columnService.findAllByTitle("%"+this.columnTitle+"%"));
        /*专栏列表*/
        m.addAttribute("columns",columnService.listTop(20));
        /*分类排行列表*/
        m.addAttribute("types",typeService.listTypeTop(10000));
        /*标签排行列表*/
        m.addAttribute("tags",tagService.listTagTop(10000));
        /*推荐博客列表*/
        m.addAttribute("recommend",blogService.listRecommendBlogTop(20));
        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        /*右边分类详情*/
        right(m);

        return "index-column";
    }

    /*column分类后翻页*/
    @PostMapping("/index/columns/flip")
    public String goColumnFlip(Model m, @PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){

        m.addAttribute("page",blogService.listBlog(pageable,blogQueryColumn));

        return "index-column::BlogPage";

    }



    /*跳转博客详情页面*/
    @GetMapping("/blog")
    public String goBlog(Model m){
        /*歌曲列表*/
        addMusicList(m);
        /*侧边的图片*/
        addRightImgList(m);
        /*pixiv日榜*/
        addPixivList(m);
        /*加载页面随机图片*/
        randomImgLoad(m);

        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        List<Column> columns = columnService.listTop(20);
        m.addAttribute("columns",columns);
        /*分类排行列表*/
        m.addAttribute("types",typeService.listTypeTop(10000));
        /*标签排行列表*/
        m.addAttribute("tags",tagService.listTagTop(10000));
        /*推荐博客列表*/
        m.addAttribute("recommend",blogService.listRecommendBlogTop(20));
        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        /*右边分类详情*/
        right(m);

        /*用户是否登录*/
        m.addAttribute("isUser","yes");

        return "blog";
    }

    @GetMapping("/blog/{id}")
    public String goBlog(Model m, @PathVariable Long id, HttpSession session) throws NotFoundException {
        /*歌曲列表*/
        addMusicList(m);
        /*侧边的图片*/
        addRightImgList(m);
        /*pixiv日榜*/
        addPixivList(m);
        /*加载页面随机图片*/
        randomImgLoad(m);

        /*专栏详细列表*/
        m.addAttribute("columnsDetails",columnService.findAllByTitle("%"+this.columnTitle+"%"));
        /*专栏列表*/
        m.addAttribute("columns",columnService.listTop(20));
        /*分类排行列表*/
        m.addAttribute("types",typeService.listTypeTop(10000));
        /*标签排行列表*/
        m.addAttribute("tags",tagService.listTagTop(10000));
        /*推荐博客列表*/
        m.addAttribute("recommend",blogService.listRecommendBlogTop(20));
        /*背景轮播图片 */
        m.addAttribute("imgFlows",imgFlowService.findAll());

        /*右边分类详情*/
        right(m);



        Blog blog = blogService.getAndConvert(id);
        if(blog==null){
            return "redirect:/index";
        }
        m.addAttribute("blog",blog);

        /*用户是否登录*/
        m.addAttribute("isUser","yes");


        return "blog";
    }

   /*专栏详情的ajax*/
   @PostMapping("/index/column-search")
   public String seachColumnTitle(String columnTitle,Model model){
       this.columnTitle = columnTitle;
       /*专栏详细列表*/
       model.addAttribute("columnsDetails",columnService.findAllByTitle("%"+this.columnTitle+"%"));
       List<Column> list = columnService.findAllByTitle("%"+this.columnTitle+"%");
       return "_fragments :: columnMuban";
   }

     public void right(Model m){
         /*右侧分类详情*/
         BlogQuery bysjxs = new BlogQuery();
         /*496-视觉小说*/
         bysjxs.setTypeId((long)496);
         m.addAttribute("sjxs",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*528-插画*/
         bysjxs.setTypeId((long)528);
         m.addAttribute("ch",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*529-后端*/
         bysjxs.setTypeId((long)529);
         m.addAttribute("hd",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*530-前端*/
         bysjxs.setTypeId((long)530);
         m.addAttribute("qd",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*531-汽车*/
         bysjxs.setTypeId((long)531);
         m.addAttribute("qc",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*532-动漫*/
         bysjxs.setTypeId((long)532);
         m.addAttribute("dm",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*533-音乐*/
         bysjxs.setTypeId((long)533);
         m.addAttribute("yy",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*534-影视*/
         bysjxs.setTypeId((long)534);
         m.addAttribute("ys",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*535-游戏*/
         bysjxs.setTypeId((long)535);
         m.addAttribute("yx",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*536-生活*/
         bysjxs.setTypeId((long)536);
         m.addAttribute("sh",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*545-日语*/
         bysjxs.setTypeId((long)545);
         m.addAttribute("ry",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*538-杂谈*/
         bysjxs.setTypeId((long)538);
         m.addAttribute("zt",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*539-学习*/
         bysjxs.setTypeId((long)539);
         m.addAttribute("xx",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*546-软件*/
         bysjxs.setTypeId((long)546);
         m.addAttribute("rj",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*554-应用软件*/
         bysjxs.setTypeId((long)554);
         m.addAttribute("yyrj",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
         /*600-段子*/
         bysjxs.setTypeId((long)600);
         m.addAttribute("dz",blogService.listBlogByType(new Sort(Sort.Direction.DESC,"updateTime"),bysjxs));
     }

     public void addMusicList(Model m){
         /*歌曲列表*/
     /* String json = getMusic.loadJson("https://api.quyihuang.cn/couldmusiceasy/type=playlist?id=4953969685");
         JSONArray jo = JSON.parseArray(json);
         List<Map<String,String>> list1 = new ArrayList<>();
         for (int i = 0; i < jo.size(); i++) {
             list1.add((Map<String, String>) jo.get(i));
         }
         m.addAttribute("MusicList",list1);*/
     }

     public void addRightImgList(Model m){
         /*侧边的图片*/
         List<ImgSide> list = imgService.findAll();
         String s = "img";
         for (int i = 0; i < list.size(); i++) {
             m.addAttribute(s+i,list.get(i).getUrl());
         }
     }

     public void addPixivList(Model m){
        /* String url = "https://www.moe123.net/api/images?skip=0&nextToken=undefined";
         String content = getMusic.loadJson(url);
         Map<String,Object> map1 = (Map)JSON.parse(content);
         List<Map<String,Object>> list = (List<Map<String, Object>>) map1.get("items");*/
         m.addAttribute("pixivList",null);
     }


    @GetMapping("/css")
    public String gocs(Model m,@PageableDefault(size=10,sort={"updateTime"},direction= Sort.Direction.DESC)Pageable pageable){
        m.addAttribute("isUser","yes");

        return "cs";
    }

    @PostMapping("/index/csss")
    @ResponseBody
    public Object xxx(String urlData){
        String url = "https://www.moe123.net/api/images?skip=32&nextToken=undefined";
        String content = getMusic.loadJson(url);
        return  JSON.parse(content);
    }


    public void randomImgLoad(Model model){
        Random r = new Random();
        List<ImgFlow> imgFlows = imgFlowService.findAll();
        int n = (int)Math.ceil(r.nextInt(imgFlows.size()));
        model.addAttribute("backImg",imgFlows.get(n));
    }

    public static void main(String[]args){
        String url = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/dynamic_new?uid=336562191&type_list=8,512,4097,4098,4099,4100,4101";
        String content = getMusic.loadJson(url);
        System.out.println(content);
        System.out.println(new Date().getTime());
    }

}
