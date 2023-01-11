package com.example.demo.master.service;

import com.example.demo.master.entity.SSaler;

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
public interface ISSalerService {

    SSaler selectByPrimaryKey(String salerId, String salerNo) throws Exception;

}
