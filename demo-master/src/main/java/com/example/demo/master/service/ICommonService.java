package com.example.demo.master.service;

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
public interface ICommonService {

    /**
     * 获取序列当前值
     * @param sequenceName
     * @return
     */
    String getCurrentValue(String sequenceName);

    /**
     * 获取序列下一个值
     * @param sequenceName
     * @return
     */
    String getNextValue(String sequenceName);

}
