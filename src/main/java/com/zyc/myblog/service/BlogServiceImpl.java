package com.zyc.myblog.service;

import com.zyc.myblog.dao.BlogRepository;
import com.zyc.myblog.po.Blog;
import com.zyc.myblog.po.Type;
import com.zyc.myblog.util.MarkdownUtils;
import com.zyc.myblog.util.MyBeanUtils;
import com.zyc.myblog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    private BlogRepository blogRepository;

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) throws NotFoundException {
        Blog blog = blogRepository.findById(id).orElse(null);
        if(blog==null){
            throw new NotFoundException("不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtml(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        Page<Blog> p = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(blog.getTitle()!=null && !"".equals(blog.getTitle())){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.getColumnId()!=null){
                    predicates.add(cb.equal(root.<Type>get("column").get("id"),blog.getColumnId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
        return p;
    }

    @Override
    public List<Blog> listBlogByColumn(Sort sort, BlogQuery blog) {
        List<Blog> p = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.<Type>get("column").get("id"),blog.getColumnId()));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },sort);
        return p;
    }
    @Override
    public List<Blog> listBlogByType(Sort sort, BlogQuery blog) {
        List<Blog> p = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },sort);
        return p;
    }



    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null) {
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) throws NotFoundException {
        Blog b = blogRepository.findById(id).orElse(null);
        if(b==null){
             throw new NotFoundException("该blog不存在");
        }
        blog.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBolg(Long id) {
         blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return blogRepository.findTop(pageable);
    }


}
