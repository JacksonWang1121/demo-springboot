package com.example.demo.master.controller;

import com.example.demo.master.service.IAppletsService;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/applets")
public class AppletsController {

    //log4j日志
//    private static final Logger LOGGER = Logger.getLogger(AppletsController.class);
    //logback日志
    private static final org.slf4j.Logger LOGGER_LOGBACK = LoggerFactory.getLogger(AppletsController.class);

    @Resource
    private IAppletsService appletsService;

    public IAppletsService getAppletsService() {
        return appletsService;
    }

    /**
     * 发送微信消息通知接口
     * consumes（输入）：过滤请求头ContentType，限定允许的ContentType的类型
     * produces（输出）：标注后影响最终传输的ContentType的类型
     *
     * @author hacker
     */
    @RequestMapping(value = "/sendMsg",
            method = {RequestMethod.POST, RequestMethod.GET},
            consumes = "application/*;charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public String sendMsg() throws Exception {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("sendMsg");
        LOGGER_LOGBACK.info("---------------------------Applets Send Message Start---------------------------");
        appletsService.sendMsg();
        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------Applets Send Message End, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
        return "success";
    }

}
