package com.example.demospringboot.exception;

import com.example.demospringboot.entity.CommonResponseEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>异常捕获</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/11/11 16:55
 */

@ControllerAdvice
@Configuration
public class CommonExceptionHandler {

    /**
     * 捕获CommonException异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public ResponseEntity<CommonResponseEntity> commonExceptionHandler(CommonException ex) {
        //甚至错误信息返回页面
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseEntity.error(ex.getMessageEnums()));
    }

}
