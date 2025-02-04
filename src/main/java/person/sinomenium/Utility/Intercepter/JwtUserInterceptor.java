package person.sinomenium.Utility.Intercepter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import person.sinomenium.Static.Constant.UserConstant;
import person.sinomenium.Utility.Exception.LoginCheckException;
import person.sinomenium.Utility.Properties.JwtProperties;
import person.sinomenium.Static.Tool.ThreadContext;
import person.sinomenium.Utility.Util.JwtUtil;

/**
 * 在Interceptor中配置拦截位置
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtUserInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            // 如果是预检请求，则直接通过，不进行后续处理
            return true;
        }

        log.info("当前线程为：{}", Thread.currentThread().threadId());
        //1、从请求头中获取令牌和路径（用于区分用户和管理员）
        String token = request.getHeader("token");
        String url = String.valueOf(request.getRequestURL());
        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            //token令牌中解析后的id信息
            Integer userId = Integer.valueOf(claims.get("user_id").toString());
            Integer admin = Integer.valueOf(claims.get("admin").toString());
            log.info("当前用户id：{},管理权限为：{}", userId, (admin.equals(UserConstant.USER) ?"用户":(admin.equals(UserConstant.ADMIN) ? "管理员":"超级管理员") + admin));
            //检验管理员接口是否为管理员访问
            if (url.contains("root"))
                if (!(admin.equals(UserConstant.SUPER_ADMIN)||admin.equals(UserConstant.ADMIN)))
                    throw new Exception();
            log.info("访问通过");
            //通过threadLocal方法在ThreadLocalMap中中存储当前线程的用户信息
            ThreadContext.setCurrentId(userId);
            ThreadContext.setAdmin(admin);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            //response.setStatus(401);
            throw new LoginCheckException("权限校验未通过");
        }
    }

    //为防止内存泄漏，可重写拦截器的afterCompletion方法在每次请求结束后删除存储信息
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable Exception ex) {
        ThreadContext.removeAll();
    }

}

