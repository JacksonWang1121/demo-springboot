package com.example.demospringboot.service;

import com.example.demospringboot.entity.SBaffleConfig;
import com.github.pagehelper.PageInfo;

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
 * @Description
 * @date 2022/10/17 10:39
 */
public interface IMybatisService {

    PageInfo<SBaffleConfig> selectToPageHelper(int pageNum, int pageSize) throws Exception;

}
