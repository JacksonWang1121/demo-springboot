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
 * @date 2022/11/18 14:19
 */
public interface IParseIdCardService {

    public Map<String, String> parseIdCard(String applCode, String regAddr) throws Exception;

}
