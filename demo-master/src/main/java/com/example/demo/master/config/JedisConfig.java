package com.example.demo.master.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
 * @date 2023/1/3 16:43
 */

@Configuration
public class JedisConfig {

        /**
         * 自定义Key为String类型Value为Object类型的Redis操作模板
         */
        @Bean(name = "redisTemplate")
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);

//            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            // key采用String的序列化方式
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            // hash的key也采用String的序列化方式
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());

            return redisTemplate;
        }

}
