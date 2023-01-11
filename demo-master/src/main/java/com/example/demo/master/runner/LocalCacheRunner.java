package com.example.demo.master.runner;

import com.example.demo.master.constance.PublicConstance;
import com.example.demo.master.entity.SConfig;
import com.example.demo.master.entity.SDictionary;
import com.example.demo.master.entity.SMessage;
import com.example.demo.master.mapper.SConfigMapper;
import com.example.demo.master.mapper.SDictionaryMapper;
import com.example.demo.master.mapper.SMessageMapper;
import com.example.demo.master.util.LocalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>应用启动自定义初始化工作</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/10/19 17:02
 */

@Order(1)
@Component
public class LocalCacheRunner implements ApplicationRunner {

    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(LocalCacheRunner.class);

    @Autowired
    private SConfigMapper sConfigMapper;

    @Autowired
    private SDictionaryMapper sDictionaryMapper;

    @Autowired
    private SMessageMapper sMessageMapper;

    @Override
    public void run(ApplicationArguments args) {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("LocalCacheRunner");
        LOGGER_LOGBACK.info("---------------------------LocalCacheRunner Start---------------------------");

        try {
            //Init SConfig Start
            initSConfig();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //Init SDictionary Start
            initSDictionary();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //Init SMessage Start
            initSMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------LocalCacheRunner, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
    }

    /**
     * 任务调度
     */
    @Scheduled(cron = "0 0/10 8-20 * * ?")
    private void initSConfig() {
        LOGGER_LOGBACK.info(">>>>>>>>>>>>>Init SConfig Start<<<<<<<<<<<<<");
        Map<String, String> sConfigMap = new HashMap<>();
        //查询所有生效配置数据
        SConfig domain = new SConfig();
        domain.setCfgSts(PublicConstance.STATUS_VALID);
        List<SConfig> sConfigList = sConfigMapper.selectAll(domain);
        for (SConfig sConfig : sConfigList) {
            sConfigMap.put(sConfig.getCfgName(), sConfig.getCfgValue());
        }
        //将生效配置放入本地缓存中
        LocalCache.sConfigMap = sConfigMap;
    }

    /**
     * 任务调度
     */
    @Scheduled(cron = "0 0/10 8-20 * * ?")
    private void initSDictionary() {
        LOGGER_LOGBACK.info(">>>>>>>>>>>>>Init SDictionary Start<<<<<<<<<<<<<");
        Map<String, Map<String, String>> sDictionaryMap = new HashMap<>();
        //查询所有生效的通用代码类别
        SDictionary domain = new SDictionary();
        domain.setDictSts(PublicConstance.STATUS_VALID);
        List<SDictionary> dictTypeList = sDictionaryMapper.selectAllToDictType(domain);
        for (SDictionary dictTypeDomain : dictTypeList) {
            Map<String, String> dictCodeMap = new HashMap<>();
            //查询所有生效的通用代码配置
            domain.setDictType(dictTypeDomain.getDictType());  //通用代码类别
            List<SDictionary> sDictionaryList = sDictionaryMapper.selectAll(domain);
            for (SDictionary sDictionary : sDictionaryList) {
                dictCodeMap.put(sDictionary.getDictCode(), sDictionary.getDictCodeDesc());
            }
            sDictionaryMap.put(dictTypeDomain.getDictType(), dictCodeMap);
        }
        //将生效系统消息放入本地缓存中
        LocalCache.sDictionaryMap = sDictionaryMap;
    }

    /**
     * 任务调度
     */
    @Scheduled(cron = "0 0/10 8-20 * * ?")
    private void initSMessage() {
        LOGGER_LOGBACK.info(">>>>>>>>>>>>>Init SMessage Start<<<<<<<<<<<<<");
        Map<String, String> sMessageMap = new HashMap<>();
        //查询所有生效系统消息数据
        SMessage domain = new SMessage();
        domain.setMsgSts(PublicConstance.STATUS_VALID);
        List<SMessage> sMessageList = sMessageMapper.selectAll(domain);
        for (SMessage sMessage : sMessageList) {
            sMessageMap.put(sMessage.getMsgCode(), sMessage.getMsgDesc());
        }
        //将生效系统消息放入本地缓存中
        LocalCache.sMessageMap = sMessageMap;
    }
}
