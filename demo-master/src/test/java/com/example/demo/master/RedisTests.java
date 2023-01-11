package com.example.demo.master;

import com.example.demo.master.controller.AppletsController;
import com.example.demo.master.service.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Set;

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
 * @date 2023/1/3 15:20
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTests {

    //logback日志
    private static final org.slf4j.Logger LOGGER_LOGBACK = LoggerFactory.getLogger(AppletsController.class);

    @Resource
    private IRedisService redisService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("testRedis");
        LOGGER_LOGBACK.info(">>>>>>>>>>Test Redis Start<<<<<<<<<<");

        //批量插入测试数据
        for (int i = 0; i < 10; i++) {
            redisService.setString("test_redis_key_" + i, "test_redis_value_" + i);
        }

        Set<String> keys = redisService.keys("test_redis_key_*");
        for (String key: keys) {
            String value = redisService.getString(key);
            System.out.println("key: " + key + ", value: " + value);
        }

        //批量删除测试数据


        stopWatch.stop();
        LOGGER_LOGBACK.info(">>>>>>>>>>Test Redis End, Take " + stopWatch.getTotalTimeSeconds() + "s<<<<<<<<<<");
    }

}
