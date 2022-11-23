package com.example.demospringboot.entity;

import com.example.demospringboot.constance.PublicConstance;
import com.example.demospringboot.enums.MessageEnums;
import com.example.demospringboot.util.LocalCache;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>返回页面的数据实体</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/10/19 16:15
 */
public class CommonResponseEntity<T> {

    private String resultCode;  //返回结果码：0000处理成功，9999处理异常

    private String resultDesc;  //返回结果描述

    private T data;  //返回结果集存储

    public CommonResponseEntity() {

    }

    public CommonResponseEntity(String resultCode) {
        this.resultCode = resultCode;
        this.resultDesc = LocalCache.sMessageMap.get(resultCode);
    }

    public CommonResponseEntity(String resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;

        /*if(StringUtils.hasText(resultCode) && StringUtils.isEmpty(resultDesc)) {
            this.resultDesc = LocalCache.sMessageMap.get(resultCode);
        }*/
    }

    public CommonResponseEntity(String resultCode, T data) {
        this.resultCode = resultCode;
        this.resultDesc = LocalCache.sMessageMap.get(resultCode);
        this.data = data;
    }

    public CommonResponseEntity(String resultCode, String resultDesc, T data) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.data = data;

        /*if(StringUtils.hasText(resultCode) && StringUtils.isEmpty(resultDesc)) {
            this.resultDesc = LocalCache.sMessageMap.get(resultCode);
        }*/
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                ", data=" + data.toString() +
                '}';
    }

    public boolean isSuccess() {
        if(PublicConstance.RESPONSE_CODE_SUCCESS.equals(this.resultCode)) {
            return true;
        }
        return false;
    }


    /**
     * 以下4个静态方法可以使用泛型，即能返回查询的数据
     * @param resultCode
     * @return
     */
    public static <T> CommonResponseEntity<T> status(String resultCode) {
        return new CommonResponseEntity<T>(resultCode);
    }

    public static <T> CommonResponseEntity<T> status(String resultCode, String resultDesc) {
        return new CommonResponseEntity<T>(resultCode, resultDesc);
    }

    public static <T> CommonResponseEntity<T> status(String resultCode, T data) {
        return new CommonResponseEntity<T>(resultCode, data);
    }

    public static <T> CommonResponseEntity<T> status(String resultCode, String resultDesc, T data) {
        return new CommonResponseEntity<T>(resultCode, resultDesc, data);
    }

    public static CommonResponseEntity success() {
        return status(PublicConstance.RESPONSE_CODE_SUCCESS);
    }

    public static CommonResponseEntity failed() {
        return status(PublicConstance.RESPONSE_CODE_FAILED);
    }

    public CommonResponseEntity<T> addData(T data) {
        this.data = data;
        return this;
    }

    public static <T> CommonResponseEntity<T> success(MessageEnums messageEnums, T data) {
        return new CommonResponseEntity<T>(messageEnums.getCode(), messageEnums.getMessage(), data);
    }

    public static <T> CommonResponseEntity<T> error(MessageEnums messageEnums) {
        return new CommonResponseEntity<T>(messageEnums.getCode(), messageEnums.getMessage());
    }
}
