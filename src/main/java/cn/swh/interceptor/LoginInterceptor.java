package cn.swh.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by limi on 2017/10/15.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * @Title  preHandle
     * @Author  shenwenhao
     * @Description: 预处理 在请求未到达之前的操作,拦截未登陆的操作
     * @Date: 22:00 2020/11/18
     * @Param: [request, response, handler]
     * @return: boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
