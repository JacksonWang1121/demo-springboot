package com.example.demo.master.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.master.entity.SSaler;
import com.example.demo.master.service.ISSalerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>无</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2023/1/5 10:34
 */

@Component
public class JwtInterceptor implements HandlerInterceptor {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private ISSalerService sSalerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER_LOGBACK.info(">>>>>>>>>>JWT Interceptor Start<<<<<<<<<<<");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)) {
            LOGGER_LOGBACK.info(">>>>>>>>>>JWT Interceptor Ignored<<<<<<<<<<<");
            return true;
        }
        //从HTTP请求头中取出token
        String token = request.getHeader("token");
        if(token == null) {
            throw new Exception("Token令牌丢失");
        }

        JWT jwt = JWTUtil.parseToken(token);
        //获取token中的登录用户名
        String username = String.valueOf(jwt.getPayload("username"));
        //查询登录用户信息
        SSaler sSaler = sSalerService.selectByPrimaryKey(null ,username);
        if(sSaler == null) {
            throw new Exception("未获取到用户信息");
        }
        //jwt验证
        boolean verify = JWTUtil.verify(token, sSaler.getPassword().getBytes());
        if(verify) {
            LOGGER_LOGBACK.info(">>>>>>>>>>JWT Interceptor Passed<<<<<<<<<<<");
            return true;
        }

        LOGGER_LOGBACK.info(">>>>>>>>>>JWT Interceptor Intercepted<<<<<<<<<<<");

        JSONObject respJson = new JSONObject();
        respJson.put("code", "401");
        respJson.put("message", "JWT Interceptor Intercepted");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(respJson);
        response.getWriter().flush();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
