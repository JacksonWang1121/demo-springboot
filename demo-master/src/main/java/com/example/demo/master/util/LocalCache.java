package com.example.demo.master.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * <p>
 * <h2>简述</h2>
 * 		<ol>本地缓存控制器,用于少量数据缓存使用</ol>
 * <h2>功能描述</h2>
 * 		<ol>支持数据过期处理</ol>
 * <h2>修改历史</h2>
 *      <ol>如有修改，添加修改历史</ol>
 * </p>
 * @author Sjun
 * @Date 2020年3月22日 下午3:30:40
 * @version 1.0
 */
public class LocalCache implements DisposableBean{

    /**
     * 	开启一定定时器 执行过期数据的清理
     */
    private ScheduledExecutorService swapExpiredPool = new ScheduledThreadPoolExecutor(1);

    /**
     *    缓存写入锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 	缓存集合
     */
    private Map<String, Node> cache = new ConcurrentHashMap<>(1024);


    /**
     * 	让过期时间最小的数据排在队列前，在清除过期数据时 ，只需查看缓存最近的过期数据，而不用扫描全部缓存
     * @see Node#compareTo(Node)
     * @see SwapExpiredNodeWork#run()
     */
    private PriorityQueue<Node> expireQueue = new PriorityQueue<>(1024);

    /**
     * 系统配置缓存集合
     */
    public static Map<String, String> sConfigMap = new HashMap<>();

    /**
     * 系统通用代码缓存集合
     */
    public static Map<String, Map<String, String>> sDictionaryMap = new HashMap<>();

    /**
     * 系统消息缓存集合
     */
    public static Map<String, String> sMessageMap = new HashMap<>();

    /**
     * 	构造函数 默认5秒清理一次过期数据
     */
    public LocalCache() {
        this(5);
    }

    /**
     * 	构造函数 设置数据清理间隔
     *  @param delay
     */
    public LocalCache(long delay) {
        swapExpiredPool.scheduleWithFixedDelay( new SwapExpiredNodeWork(), delay, delay, TimeUnit.SECONDS);
    }

    /**
     *
     * <p>
     * <h2>简述</h2>
     * 		<ol>添加缓存信息</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     * @author Sjun
     * @Date 2020年3月22日 下午3:18:28
     * @version 1.0
     * @param key
     * @param value
     * @param ttl 过期时间 （毫秒）
     * @return
     */
    public Object set(String key, Object value, long ttl) {
        Assert.isTrue(StringUtils.hasLength(key), "key can't be empty");
        Assert.isTrue(ttl > 0, "ttl must greater than 0");

        long expireTime = System.currentTimeMillis() + ttl;
        Node newNode = new Node(key, value, expireTime);
        lock.lock();
        try {
            Node old = cache.put(key, newNode);
            expireQueue.add(newNode);
            //如果该key存在数据，还要从过期时间队列删除
            if (old != null) {
                expireQueue.remove(old);
                return old.value;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * <p>
     * <h2>简述</h2>
     * 		<ol>获取缓存中的数据</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     * @author Sjun
     * @Date 2020年3月22日 下午3:17:21
     * @version 1.0
     * @param key
     * @return
     */
    public Object get(String key) {
        Node n = cache.get(key);
        //增加过期判定
        if(n!=null && n.expireTime<System.currentTimeMillis()){
            return null;
        }
        return n == null ? null : n.value;
    }

    /**
     *
     * <p>
     * <h2>简述</h2>
     * 		<ol>请添加简述</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     * @author Sjun
     * @Date 2020年3月22日 下午3:17:45
     * @version 1.0
     * @param key
     * @return
     */
    public Object remove(String key) {
        lock.lock();
        try {
            Node n = cache.remove(key);
            if (n == null) {
                return null;
            } else {
                expireQueue.remove(n);
                return n.value;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * <p>
     * <h2>简述</h2>
     * 		<ol>删除过期数据的定时任务线程</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     * @author Sjun
     * @Date 2020年3月22日 下午3:20:37
     * @version 1.0
     */
    private class SwapExpiredNodeWork implements Runnable {

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            while (true) {
                lock.lock();
                try {
                    Node node = expireQueue.peek();
                    //没有数据了，或者数据都是没有过期的了
                    if (node == null || node.expireTime > now) {
                        return;
                    }
                    cache.remove(node.key);
                    expireQueue.poll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     *
     * <p>
     * <h2>简述</h2>
     * 		<ol>缓存数据</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     * @author Sjun
     * @Date 2020年3月22日 下午3:22:09
     * @version 1.0
     */
    private static class Node implements Comparable<Node> {
        private String key;
        private Object value;
        private long expireTime;

        public Node(String key, Object value, long expireTime) {
            this.value = value;
            this.key = key;
            this.expireTime = expireTime;
        }


        /**
         *
         * <p>
         * <h2>简述</h2>
         * 		<ol>支持排序，便于处理过期数据</ol>
         * <h2>功能描述</h2>
         * 		<ol>请添加功能详细的描述</ol>
         * <h2>修改历史</h2>
         *      <ol>如有修改，添加修改历史</ol>
         * </p>
         * @author Sjun
         * @Date 2020年3月22日 下午3:21:35
         * @version 1.0
         * @param o
         * @return
         * @see Comparable#compareTo(Object)
         */
        @Override
        public int compareTo(Node o) {
            long r = this.expireTime - o.expireTime;
            if (r > 0) {
                return 1;
            }
            if (r < 0) {
                return -1;
            }
            return 0;
        }
    }

    @Override
    public void destroy() throws Exception {
        swapExpiredPool.shutdown();
    }


    public static LocalCache getInstance() {
        return null;
    }

    public static String translateSDictionary(String dictType, String dictCode) throws Exception {
        if(StringUtils.hasText(dictType)) {
            Map<String, String> dictTypeMap = sDictionaryMap.get(dictType);
            if(StringUtils.hasText(dictCode) && dictTypeMap.containsKey(dictCode)) {
                return dictTypeMap.get(dictCode);
            }
        }
        return "";
    }
}
