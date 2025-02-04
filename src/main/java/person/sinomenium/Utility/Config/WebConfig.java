package person.sinomenium.Utility.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import person.sinomenium.Utility.Intercepter.JwtUserInterceptor;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtUserInterceptor jwtUserInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("跨域配置成功");
        registry.addMapping("/**")
                .allowedOrigins("http://82.157.184.71:888/") // 允许所有源访问
                .allowedMethods("*") // 允许所有HTTP方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 允许发送凭证
                .maxAge(1800);
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("添加拦截器");
        String user = "/qingteng-recruitment/user";
        String root = "/qingteng-recruitment/root";
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jwtUserInterceptor);
        setRouter(interceptorRegistration,user);
        setRouter(interceptorRegistration,root);
    }
    //设置管理员与用户路由
    private void setRouter(InterceptorRegistration interceptorRegistration,String name){
        interceptorRegistration.addPathPatterns(name + "/**")
                .excludePathPatterns(name + "/login")
                .excludePathPatterns(name + "/emailLogin")
                .excludePathPatterns(name + "/sendEmail")
                .excludePathPatterns(name + "/register");
    }

}