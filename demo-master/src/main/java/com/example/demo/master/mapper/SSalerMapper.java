package com.example.demo.master.mapper;

import com.example.demo.master.entity.SSaler;
import org.apache.ibatis.annotations.Param;

public interface SSalerMapper {
    int deleteByPrimaryKey(String salerId);

    int insert(SSaler record);

    int insertSelective(SSaler record);

    SSaler selectByPrimaryKey(@Param("salerId") String salerId, @Param("salerNo") String salerNo);

    int updateByPrimaryKeySelective(SSaler record);

    int updateByPrimaryKey(SSaler record);

    int loginOnAuto(@Param("salerNo") String salerNo, @Param("password") String password);
}