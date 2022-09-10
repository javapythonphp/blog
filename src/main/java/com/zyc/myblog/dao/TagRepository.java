package com.zyc.myblog.dao;

import com.zyc.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag getTagByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
