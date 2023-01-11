package com.example.demo.master.config;

import com.example.demo.master.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>登录拦截器配置</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2023/1/5 16:40
 */

@Configuration
public class LoginAutoInterceptorConfig implements WebMvcConfigurer {

    /**
     * 设置拦截路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/login/**")
                .excludePathPatterns("/login/loginOnAuto");
    }

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "PSOT", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(24 * 60 * 60);  //登录过期时间，秒数
    }

    /**
     * 将拦截器注入Context
     * @return
     */
    @Bean
    public JwtInterceptor authInterceptor() {
        return new JwtInterceptor();
    }
}
