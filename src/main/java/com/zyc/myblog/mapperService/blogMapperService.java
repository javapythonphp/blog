package com.zyc.myblog.mapperService;

import com.zyc.myblog.po.*;

import java.util.List;

public interface blogMapperService {

    public List<Blog> findAll();

    public Type findType(Long id);

    public Column findColumn(Long id);

    public List<Tag> findTags(Long id);

    public User findUser(Long id);

}
