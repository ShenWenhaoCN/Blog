package cn.swh.controller;

import cn.swh.util.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName CaptchaController
 * @Description:
 * @Author: shenwenhao
 * @Date: 2020/11/18 10:09
 * @Version: 1.0
 **/
@Controller
public class CaptchaController {

    //评论页面获得验证码
    @GetMapping("/captcha")
    public String getCaptcha(HttpSession session, Model model) throws Exception {
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if(session.getAttribute("captcha") != null) {
            session.getAttribute("captcha");
        }
        session.setAttribute("captcha", captcha.get("str"));
        model.addAttribute("captcha", captcha.get("image"));
        return "blog :: captcha-Img";
    }

    //登陆页面获得验证码
    @GetMapping("/captchaLogin")
    public String getCaptchaLogin(HttpSession session, Model model) throws Exception {
        Map<String, String> captcha = CaptchaUtil.getCaptcha(4);
        if(session.getAttribute("captchaLogin") != null) {
            session.getAttribute("captchaLogin");
        }
        session.setAttribute("captchaLogin", captcha.get("str"));
        model.addAttribute("captchaLogin", captcha.get("image"));
        return "admin/login :: captcha-Img";
    }
}
