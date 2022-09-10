package com.zyc.myblog.service;

import com.zyc.myblog.po.Blog;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface BlogService {

     Blog getBlog(Long id);

     Blog getAndConvert(Long id) throws NotFoundException;

     Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

     Page<Blog> listBlog(Pageable pageable);

     Page<Blog> listBlog(String query,Pageable pageable);

     Page<Blog> listBlog(Long tagId,Pageable pageable);

     List<Blog> listBlogByColumn(Sort sort, BlogQuery blog);

     Blog saveBlog(Blog blog);

     Blog updateBlog(Long id,Blog blog) throws NotFoundException;

     void deleteBolg(Long id);

     List<Blog> listRecommendBlogTop(Integer size);

     List<Blog> listBlogByType(Sort sort, BlogQuery blog);

}
