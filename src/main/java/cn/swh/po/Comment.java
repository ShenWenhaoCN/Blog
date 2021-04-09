package cn.swh.po;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limi on 2017/10/14.
 */
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    //添加评论的用户名
    private String nickname;
    //添加评论的邮箱
    private String email;
    //评论内容
    private String content;
    //评论头像
    private String avatar;
    //评论创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //评论关联的博客
    @ManyToOne
    private Blog blog;
    //提交评论的验证码:不存入数据库
    @Transient
    private String captchacode;

    //一个对象可以有多个子对象
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    //只有一个父对象
    @ManyToOne
    private Comment parentComment;

    private boolean adminComment;

    public Comment() {
    }

    public String getCaptchacode() {
        return captchacode;
    }

    public void setCaptchacode(String captchacode) {
        this.captchacode = captchacode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}
