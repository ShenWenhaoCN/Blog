package cn.swh.controller;

import cn.swh.po.User;
import cn.swh.service.BlogService;
import cn.swh.service.CommentService;
import cn.swh.service.TagService;
import cn.swh.service.TypeService;
import cn.swh.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by limi on 2017/10/13.
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    /**
     * @Title  index
     * @Author  shenwenhao
     * @Description: 首页
     * @Date: 10:46 2020/11/18
     * @Param: [pageable, model]
     * @return: java.lang.String
     **/
    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }


    /**
     * @Title  search
     * @Author  shenwenhao
     * @Description: 搜索
     * @Date: 10:46 2020/11/18
     * @Param: [pageable, query, model]
     * @return: java.lang.String
     **/
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * @Title  blog
     * @Author  shenwenhao
     * @Description: 博客详情
     * @Date: 10:47 2020/11/18
     * @Param: [id, model]
     * @return: java.lang.String
     **/
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model, HttpSession session) throws Exception {
        model.addAttribute("blog", blogService.getAndConvert(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if (session.getAttribute("captcha") != null) {
            session.removeAttribute("captcha");
        }
        session.setAttribute("captcha", captcha.get("str"));
        model.addAttribute("captcha", captcha.get("image"));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("admin", true);
        }
        return "blog";
    }

    /**
     * @Title  newblogs
     * @Author  shenwenhao
     * @Description: footer中最新博客
     * @Date: 10:47 2020/11/18
     * @Param: [model]
     * @return: java.lang.String
     **/
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

}
