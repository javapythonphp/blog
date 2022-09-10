package com.zyc.myblog.mapper;

import com.zyc.myblog.po.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface blogMapper {
    public List<Blog> findAll();

    public Type findType(Long id);

    public Column findColumn(Long id);

    public List<Tag> findTags(Long id);

    public User findUser(Long id);

}
