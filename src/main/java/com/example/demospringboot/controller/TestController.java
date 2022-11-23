package com.example.demospringboot.controller;

import com.example.demospringboot.entity.CommonResponseEntity;
import com.example.demospringboot.enums.MessageEnums;
import com.example.demospringboot.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
 * @date 2022/10/21 17:21
 */

@RestController
@RequestMapping("/test")
public class TestController {

    //多环境配置
    @Value("${demospringboot.multiple-env-conf}")
    private String multipleEnvConf;

    @GetMapping("/exception")
    public CommonResponseEntity dealException() {
        throw new CommonException(MessageEnums.REQUEST_FAILED);
    }

    @GetMapping("/success")
    public ResponseEntity dealSuccess() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("seq", UUID.randomUUID().toString());
        resultMap.put("name", "Hello World!!!");
        return ResponseEntity.ok().body(CommonResponseEntity.success(MessageEnums.SUCCESS, resultMap));
    }

    @GetMapping("/env")
    public ResponseEntity multipleEnvConfig() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("seq", UUID.randomUUID().toString());
        resultMap.put("demospringboot.multiple-env-conf", multipleEnvConf);
        return ResponseEntity.ok().body(CommonResponseEntity.success(MessageEnums.SUCCESS, resultMap));
    }

}
