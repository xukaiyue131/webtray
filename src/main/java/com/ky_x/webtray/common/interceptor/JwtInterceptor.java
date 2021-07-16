package com.ky_x.webtray.common.interceptor;

import com.ky_x.webtray.annotation.PassToken;
import com.ky_x.webtray.utils.JwtUtil;
import com.ky_x.webtray.utils.RedisUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class JwtInterceptor implements HandlerInterceptor {
    Logger logger  = LoggerFactory.getLogger(JwtInterceptor.class);
   private RedisUtil redisUtil;
   public JwtInterceptor(RedisUtil redisUtil){
       this.redisUtil = redisUtil;
   }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头里面获取token
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注解
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken annotation = method.getAnnotation(PassToken.class);
            if(annotation.required()){
                return  true;
            }
        }else{
            if(token==null){
                logger.info("token无效，需要重新登录");
                return false;
            }
        }
        return JwtUtil.verity(token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
