package com.zyc.myblog.service;

import com.zyc.myblog.dao.CommentRepository;
import com.zyc.myblog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentSeriveImpl implements CommentService{

    private CommentRepository commentRepository;
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.ASC,"creatTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        List<Comment> comments1 = this.copyComments(comments);
        return comments1;
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment:comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments){
        for(Comment comment:comments){
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1:replys1){
                recursively(reply1);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();

    private void recursively(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply:replys){
                    recursively(reply);
            }
        }
    }

    private List<Comment> copyComments(List<Comment> comments){
        List<Comment> result = new ArrayList<>();
        for(Comment comment:comments){
            Comment comment1 = new Comment();
            BeanUtils.copyProperties(comment,comment1);
            result.add(comment1);
        }
        eachFatherComments(result);
        return result;
    }



    private void eachFatherComments(List<Comment> comments){
        for(Comment comment:comments){
            IntegratedReplies = new ArrayList<>();
            List<Comment> replies = comment.getReplyComments();
            for(Comment reply:replies){
                IntegrateSonComments(reply);
            }
            comment.setReplyComments(IntegratedReplies);
        }
    }

    private List<Comment> IntegratedReplies = new ArrayList<>();

    private void IntegrateSonComments(Comment comment){
        IntegratedReplies.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replies = comment.getReplyComments();
            for(Comment reply:replies){
                IntegrateSonComments(reply);
            }
        }
    }
}
