package com.ky_x.webtray.config;

import com.ky_x.webtray.common.interceptor.JwtInterceptor;
import com.ky_x.webtray.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtConfig implements WebMvcConfigurer {
    @Autowired
    RedisUtil redisUtil;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        registry.addInterceptor(new JwtInterceptor(redisUtil)).excludePathPatterns("/download/**","/login","/register","/sendMail","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Bean
    public JwtInterceptor getInterceptor(){
        return  new JwtInterceptor(redisUtil);
    }

}
