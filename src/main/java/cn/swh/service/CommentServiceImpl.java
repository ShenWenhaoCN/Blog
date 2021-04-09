package cn.swh.service;

import cn.swh.po.Comment;
import cn.swh.dao.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findOne(id);
    }

    /**
     * @Author: shenwenhao
     * @Description: 删除评论
     * @Date: 19:18 2020/11/17
     * @Param: [id]
     * @return: void
     **/
    @Override
    public void deleteComment(Long id) {
        //获取comment列表:其父评论和子评论列表
        Comment parentComment = getComment(id).getParentComment();
        List<Comment> comments = getComment(id).getReplyComments();
        //修改其子节点的父节点为该节点的父节点
        for(Comment c : comments) {
            c.setParentComment(parentComment);
        }
        commentRepository.delete(id);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {

        Sort sort = new Sort("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1) {
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        }
        else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {

        for(Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }

            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }

    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if(comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys) {
                if(reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
                else{
                    tempReplys.add(reply);
                }
            }
        }
    }




}
