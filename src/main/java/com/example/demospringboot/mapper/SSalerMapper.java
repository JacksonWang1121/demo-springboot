package com.example.demospringboot.mapper;

import com.example.demospringboot.entity.SSaler;

public interface SSalerMapper {
    int deleteByPrimaryKey(String salerId);

    int insert(SSaler record);

    int insertSelective(SSaler record);

    SSaler selectByPrimaryKey(String salerId);

    int updateByPrimaryKeySelective(SSaler record);

    int updateByPrimaryKey(SSaler record);
}