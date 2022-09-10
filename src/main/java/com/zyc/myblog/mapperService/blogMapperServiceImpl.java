package com.zyc.myblog.mapperService;

import com.zyc.myblog.mapper.blogMapper;
import com.zyc.myblog.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class blogMapperServiceImpl implements blogMapperService{

    private blogMapper blogMapper;
    @Autowired
    public void setBlogMapper(blogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public List<Blog> findAll() {
        return blogMapper.findAll();
    }

    @Override
    public Type findType(Long id) {
        return this.blogMapper.findType(id);
    }

    @Override
    public Column findColumn(Long id) {
        return this.blogMapper.findColumn(id);
    }

    @Override
    public List<Tag> findTags(Long id) {
        return this.blogMapper.findTags(id);
    }

    @Override
    public User findUser(Long id) {
        return this.blogMapper.findUser(id);
    }


}
