package com.example.demo.master.service.imple;

import com.example.demo.master.service.IRedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
 * @date 2022/10/18 18:01
 */

@Service("redisService")
public class RedisServiceImple implements IRedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * set redis: string类型
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void setString(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * get redis: string类型
     *
     * @param key key
     */
    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * set redis: hash类型
     *
     * @param key      key
     * @param filedKey filedKey
     * @param value    value
     * @return void
     */
    @Override
    public void setHash(String key, String filedKey, String value) {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, filedKey, value);
    }

    /**
     * get redis: hash类型
     *
     * @param key      key
     * @param filedKey filedKey
     * @return String
     */
    @Override
    public String getHash(String key, String filedKey) {
        return (String) stringRedisTemplate.opsForHash().get(key, filedKey);
    }

    /**
     * set redis:list类型
     *
     * @param key   key
     * @param value value
     * @return long
     */
    @Override
    public long setList(String key, String value) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        return listOperations.leftPush(key, value);
    }

    /**
     * get redis:list类型
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return void
     */
    @Override
    public List<String> getList(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }

    @Override
    public boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public long delete(List<String> keys) {
        return stringRedisTemplate.delete(keys);
    }

}
