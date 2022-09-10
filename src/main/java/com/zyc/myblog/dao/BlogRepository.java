package com.zyc.myblog.dao;

import com.zyc.myblog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Resource
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    @Query("select t from Blog t where t.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select t from Blog t where t.title like ?1 or t.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id=?1")
    int updateViews(Long id);


}
