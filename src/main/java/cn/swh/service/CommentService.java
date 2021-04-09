package cn.swh.service;

import cn.swh.po.Comment;

import java.util.List;

public interface CommentService {

    //通过id获得评论
    Comment getComment(Long id);

    //通过id删除评论
    void deleteComment(Long id);

    //通过博客id获得评论列表
    List<Comment> listCommentByBlogId(Long blogId);

    //保存评论
    Comment saveComment(Comment comment);
}
