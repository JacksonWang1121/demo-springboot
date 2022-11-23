package com.example.demospringboot.exception;

import com.example.demospringboot.enums.MessageEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>自定义通用异常</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/11/11 16:35
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -8889180033141402704L;
    private MessageEnums MessageEnums;

}
