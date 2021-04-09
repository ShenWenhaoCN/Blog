package cn.swh.controller;

import cn.swh.po.Comment;
import cn.swh.service.BlogService;
import cn.swh.po.User;
import cn.swh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * @Author: shenwenhao
     * @Description: 获取博客所有评论
     * @Date: 19:49 2020/11/16
     * @Param: [blogId, model, session]
     * @return: java.lang.String
     **/
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model, HttpSession session) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("admin",true);
        }
        return "blog :: commentList";
    }

    /**
     * @Author: shenwenhao
     * @Description: 添加评论
     * @Date: 19:49 2020/11/16
     * @Param: [comment, session, redirectAttributes]
     * @return: java.lang.String
     **/
    //拿到session用来判断用户身份，是管理员时在前端添加标签，在提交时候使用
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session, RedirectAttributes attributes) {
        String captcha = (String) session.getAttribute("captcha");
        Long blogId = comment.getBlog().getId();
        if(captcha == null || comment.getCaptchacode() == null || !comment.getCaptchacode().equalsIgnoreCase(captcha)) {
            //验证失败
            attributes.addFlashAttribute("message", "验证码错误，请点击验证码刷新");
            return "redirect:/comments/" + blogId;
        }
        else {
            //验证通过
            comment.setBlog(blogService.getBlog(blogId));
            User user = (User) session.getAttribute("user");
            session.removeAttribute("captcha");
            if(user != null) {
                comment.setAvatar(user.getAvatar());
                comment.setAdminComment(true);
            }
            else{
                comment.setAvatar(avatar);
            }
            commentService.saveComment(comment);
            attributes.addFlashAttribute("message", "添加回复成功");
            return "redirect:/comments/" + blogId;
        }

    }

    @GetMapping("/comments/delete/{id}")
    public String delete(@PathVariable Long id) {
        Comment comment = commentService.getComment(id);
        Long blogId = comment.getBlog().getId();
        commentService.deleteComment(id);
        return "redirect:/blog/" + blogId + "#comment-container";
    }


}
