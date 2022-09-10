package com.zyc.myblog.dao;

import com.zyc.myblog.po.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import java.util.*;

@Resource
public interface ColumnRepository extends JpaRepository<Column,Long> {

     @Query("select t from Column t where t.title like ?1")
     Page<Column> findByTitle(String title, Pageable pageable);

     @Query("select t from Column t")
     List<Column> listTop(Pageable pageable);

     @Query("select t from Column t where t.title like ?1")
     List<Column> findByTitle(String title,Sort sort);
}
