package com.example.demo.master.controller;

import com.example.demo.master.service.ILoginService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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
 * @date 2022/12/26 14:30
 */

@RestController
@RequestMapping("/login")
@Api(value = "登录测试API")
public class LoginController {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private ILoginService loginService;

    @ApiOperation(value = "新增登录用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "userno", value = "登录用户名", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户姓名", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "telephone", value = "联系方式", dataType = "String", required = true, paramType = "query")})
    @ApiResponses({@ApiResponse(code = 200, message = "保存登录用户成功"),
            @ApiResponse(code = 400, message = "保存登录用户失败")})
    @GetMapping("/saveLoginData")
    public ResponseEntity saveLoginData(@RequestParam(value = "userno", required = true) String userno,
                                        @RequestParam(value = "username", required = true) String username,
                                        @RequestParam(value = "password", required = true) String password,
                                        @RequestParam(value = "telephone", required = true) String telephone) throws Exception {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("saveLoginData");
        LOGGER_LOGBACK.info("---------------------------Save Login Data Start---------------------------");

        Map<String, String> resultMap = loginService.saveLoginData(userno, username, password, telephone);

        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------Save Login Data End, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
        return ResponseEntity.ok().body(resultMap);
    }

    @ApiOperation(value = "登录验证")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "登录用户名", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "登录密码", dataType = "String", required = true, paramType = "query")})
    @ApiResponses({@ApiResponse(code = 200, message = "登录验证成功"),
            @ApiResponse(code = 204, message = "未获取到用户信息"),
            @ApiResponse(code = 400, message = "登录验证失败")})
    @GetMapping("/loginOnAuto")
    public ResponseEntity loginOnAuto(@RequestParam(value = "username", required = true) String username,
                                  @RequestParam(value = "password", required = true) String password) throws Exception {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("loginOnAuto");
        LOGGER_LOGBACK.info("---------------------------Login On Auto Start---------------------------");

        Map<String, String> resultMap = loginService.loginOnAuto(username, password);

        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------Login On Auto End, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
        return ResponseEntity.ok().body(resultMap);
    }

}
