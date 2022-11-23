package com.example.demospringboot.controller;

import com.example.demospringboot.entity.CommonResponseEntity;
import com.example.demospringboot.entity.SBaffleConfig;
import com.example.demospringboot.service.IMybatisService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * @date 2022/10/17 10:34
 */

@RestController
@RequestMapping("/mybatis")
public class MybatisController {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(MybatisController.class);

    @Resource
    private IMybatisService mybatisService;

    /**
     * 分页查询
     * consumes（输入）：过滤请求头ContentType，限定允许的ContentType的类型
     * produces（输出）：标注后影响最终传输的ContentType的类型
     *
     * @author hacker
     */
    @RequestMapping(value = "/selectToPageHelper",
            method = {RequestMethod.GET},
            consumes = "application/*;charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public CommonResponseEntity<PageInfo<SBaffleConfig>> selectToPageHelper(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) throws Exception {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("selectToPageHelper");
        LOGGER_LOGBACK.info("---------------------------Select To Page Helper Start---------------------------");

        PageInfo<SBaffleConfig> pageInfo = mybatisService.selectToPageHelper(pageNum, pageSize);
        LOGGER_LOGBACK.info(pageInfo.getList().toString());

//        ResponseEntity<PageInfo<SBaffleConfig>> responseEntity = new ResponseEntity<>();
//        responseEntity.setResultCode(PublicConstance.RESPONSE_CODE_SUCCESS);
//        responseEntity.setData(pageInfo);

        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------Select To Page Helper, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
        return CommonResponseEntity.success().addData(pageInfo);
    }

}
