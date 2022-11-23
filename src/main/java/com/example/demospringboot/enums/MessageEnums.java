package com.example.demospringboot.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>异常消息枚举</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/11/11 16:19
 */

@AllArgsConstructor  //全参构造
@NoArgsConstructor  //无参构造
public enum MessageEnums {

    SUCCESS("0000", "处理成功"),
    SERVER_ERROR("ERR9999", "处理异常"),
    REQUEST_FAILED("REQ9999", "请求失败");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
