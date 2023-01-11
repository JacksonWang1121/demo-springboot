package com.example.demo.master.mapper;

public interface CommonMapper {

    /**
     * 获取序列当前值
     * @param sequenceName
     * @return
     */
    String getCurrentValue(String sequenceName);

    /**
     * 获取序列下一个值
     * @param sequenceName
     * @return
     */
    String getNextValue(String sequenceName);

}