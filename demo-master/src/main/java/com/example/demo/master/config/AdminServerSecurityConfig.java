package com.example.demo.master.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>配置安全校验</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2023/1/9 16:01
 */

@Configuration
public class AdminServerSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String adminSecurityContextPath;

    public AdminServerSecurityConfig(AdminServerProperties adminServerProperties) {
        this.adminSecurityContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Admin Server登录成功后，重定向到Admin Server主页面
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setTargetUrlParameter("redirectTo");
        handler.setDefaultTargetUrl(adminSecurityContextPath + "/");

        //跨域设置，Springboot Admin客户端通过instances注册，忽略这些路径的csrf保护以便admin-client注册
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(adminSecurityContextPath + "/instances",
                        adminSecurityContextPath + "/actuator/**");
        //无需登录即可访问，静态资源
        http.authorizeRequests()
                .antMatchers(adminSecurityContextPath + "/assets/**",
                        adminSecurityContextPath + "/login")
                .permitAll()
        //所有请求必须通过认证
                .anyRequest()
                .authenticated();
        //登录和登出路径，整合Springboot Admin Server UI
        http.formLogin()
                .loginPage(adminSecurityContextPath + "/login")
                .permitAll()
                .successHandler(handler);
        http.logout()
                .logoutUrl(adminSecurityContextPath + "/logout")
                .logoutSuccessUrl(adminSecurityContextPath + "/login");
        //开启http basic支持，admin-client注册时需要使用
        http.httpBasic();
    }
}
