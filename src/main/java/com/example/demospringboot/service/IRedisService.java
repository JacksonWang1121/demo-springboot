package com.example.demospringboot.service;

import java.util.List;

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
 * @date 2022/10/19 10:22
 */
public interface IRedisService {

    public void setString(String key, String value);

    public String getString(String key);

    public void setHash(String key, String filedKey, String value);

    public String getHash(String key, String filedKey);

    public long setList(String key, String value);

    public List<String> getList(String key, long start, long end);

}
