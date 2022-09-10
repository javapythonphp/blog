package com.zyc.myblog.dao;

import com.zyc.myblog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.List;

@Resource
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
