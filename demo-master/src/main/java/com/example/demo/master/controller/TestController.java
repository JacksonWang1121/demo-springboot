package com.example.demo.master.controller;

import com.example.demo.master.entity.CommonResponseEntity;
import com.example.demo.master.entity.SSaler;
import com.example.demo.master.enums.MessageEnums;
import com.example.demo.master.exception.CommonException;
import com.example.demo.master.service.ISSalerService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
@Api(value = "Swagger2测试API")
public class TestController {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(TestController.class);

    @Resource
    private ISSalerService sSalerService;

    //多环境配置
    @Value("${demomaster.multiple-env-conf}")
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

    /**
     * 多环境配置
     * @return
     */
    @GetMapping("/env")
    public ResponseEntity multipleEnvConfig() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("seq", UUID.randomUUID().toString());
        resultMap.put("demomaster.multiple-env-conf", multipleEnvConf);
        return ResponseEntity.ok().body(CommonResponseEntity.success(MessageEnums.SUCCESS, resultMap));
    }

    @ApiOperation(value = "根据登录用户名获取用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "salerId", value = "登录用户名", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "salerName", value = "姓名", dataType = "String", required = false, paramType = "query")})
    @ApiResponses({@ApiResponse(code = 200, message = "获取用户信息成功"),
            @ApiResponse(code = 204, message = "未获取到用户信息")})
    @GetMapping("/getUser")
    public ResponseEntity getUser(@RequestParam(value = "salerId", required = true) String salerId,
                                  @RequestParam(value = "salerName", required = false) String salerName) throws Exception {
        SSaler user = sSalerService.selectByPrimaryKey(salerId, null);
        return ResponseEntity.ok().body(user);
    }

    /**
     * 配置springsession信息
     * @return
     */
    @GetMapping("/springsession/set")
    public ResponseEntity setSpringSession(HttpSession httpSession) {
        httpSession.setAttribute("SID", "demo-springboot-master");
        LOGGER_LOGBACK.info("设置spring session");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("seq", UUID.randomUUID().toString());
        resultMap.put("message", "Set spring session success.");
        return ResponseEntity.ok().body(resultMap);
    }

    /**
     * 获取springsession信息
     * @return
     */
    @GetMapping("/springsession/get")
    public ResponseEntity getSpringSession(HttpSession httpSession) {
        httpSession.setAttribute("SID", "demo-springboot-master");
        LOGGER_LOGBACK.info("响应spring session");
        return ResponseEntity.ok().body(httpSession.getAttribute("SID").toString());
    }

}
