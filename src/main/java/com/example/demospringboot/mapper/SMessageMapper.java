package com.example.demospringboot.mapper;

import com.example.demospringboot.entity.SMessage;

import java.util.List;

public interface SMessageMapper {
    int deleteByPrimaryKey(String msgCode);

    int insert(SMessage record);

    int insertSelective(SMessage record);

    List<SMessage> selectAll(SMessage record);

    SMessage selectByPrimaryKey(String msgCode);

    int updateByPrimaryKeySelective(SMessage record);

    int updateByPrimaryKey(SMessage record);
}