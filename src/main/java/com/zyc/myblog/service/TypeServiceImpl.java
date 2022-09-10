package com.zyc.myblog.service;

import com.zyc.myblog.dao.TypeRepository;
import com.zyc.myblog.po.Blog;
import com.zyc.myblog.po.Type;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    private BlogService blogService;
    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    private TypeRepository typerepository;
    @Autowired
    public void setTyperepository(TypeRepository typerepository) {
        this.typerepository = typerepository;
    }

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typerepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typerepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Page<Type> ListType(Pageable pageable) {
        return typerepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) throws NotFoundException {
         Type t = typerepository.findById(id).orElse(null);
         if(t==null){
             throw new NotFoundException("无此id");
         }
         BeanUtils.copyProperties(type,t);
         return typerepository.save(t);

    }
    @Transactional
    @Override
    public void deleteType(Long id) {

         typerepository.deleteById(id);
    }

    @Override
    public Type findByName(String name) {
        return typerepository.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typerepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);

        return typerepository.findTop(pageable);
    }
}
