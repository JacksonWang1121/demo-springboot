package com.example.demo.master.service;

import java.util.Map;

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
 * @Description
 * @date 2022/12/22 16:52
 */
public interface ILoginService {

    /**
     * 新增登录用户
     * @param salerNo  登录用户名
     * @param salerName  用户姓名
     * @param password  登录密码
     * @param salerTel  联系方式
     * @return  保存结果
     */
    Map<String, String> saveLoginData(String salerNo, String salerName, String password, String salerTel);

    /**
     * 登录验证
     * @param username  登录用户名
     * @param password  登录密码
     * @return  登录验证结果
     */
    Map<String, String> loginOnAuto(String username, String password);

}
