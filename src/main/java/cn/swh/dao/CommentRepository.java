package cn.swh.dao;


import cn.swh.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //按照博客id查找顶级评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
