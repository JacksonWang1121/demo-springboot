package com.example.demo.master;

import com.example.demo.master.entity.SSaler;
import com.example.demo.master.mapper.SSalerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

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
 * @date 2022/12/23 16:31
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class MybatisTests {

    @Resource
    private SSalerMapper sSalerMapper;

    @Test
    public void test() {
        SSaler sSaler = sSalerMapper.selectByPrimaryKey("10000", null);
        System.out.println(sSaler.toString());
    }

}
