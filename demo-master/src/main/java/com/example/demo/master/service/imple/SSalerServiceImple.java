package com.example.demo.master.service.imple;

import com.example.demo.master.entity.SSaler;
import com.example.demo.master.mapper.SSalerMapper;
import com.example.demo.master.service.ISSalerService;
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

@Service("sSalerService")
public class SSalerServiceImple implements ISSalerService {

    @Resource
    private SSalerMapper sSalerMapper;

    @Override
    public SSaler selectByPrimaryKey(String salerId, String salerNo) throws Exception {
        return sSalerMapper.selectByPrimaryKey(salerId, salerNo);
    }

}
