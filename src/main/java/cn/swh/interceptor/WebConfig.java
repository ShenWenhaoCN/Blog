package cn.swh.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by limi on 2017/10/15.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * @Title  addInterceptors
     * @Author  shenwenhao
     * @Description: 添加拦截器的拦截规则
     * @Date: 22:02 2020/11/18
     * @Param: [registry]
     * @return: void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")//拦截所有的admin下的路径
                .addPathPatterns("/comments/delete/**")//拦截评论删除操作
                .excludePathPatterns("/admin")//忽略admin（登陆界面）
                .excludePathPatterns("/admin/login");//忽略admin/login（登陆表单提交页面）
    }
}
