package com.example.demo.master.service.imple;

import com.example.demo.master.mapper.CommonMapper;
import com.example.demo.master.service.ICommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
 * @date 2022/12/22 16:52
 */

@Service("commonService")
public class CommonServiceImple implements ICommonService {

    @Resource
    private CommonMapper commonMapper;

    /**
     * 获取序列当前值
     * @param sequenceName
     * @return
     */
    @Override
    public String getCurrentValue(String sequenceName) {
        return commonMapper.getCurrentValue(sequenceName);
    }

    /**
     * 获取序列下一个值
     * 注意！！！多线程调用
     * @param sequenceName
     * @return
     */
    @Override
    public String getNextValue(String sequenceName) {
        String nextValue = null;
        synchronized (Object.class) {
            nextValue = commonMapper.getNextValue(sequenceName);
        }
        return nextValue;
    }
}
