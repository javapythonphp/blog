package com.zyc.myblog.service;

import com.zyc.myblog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    void deleteComment(Long id);



}
