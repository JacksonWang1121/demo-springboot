package com.example.demo.master.mapper;

import com.example.demo.master.entity.SArea;

public interface SAreaMapper {
    int deleteByPrimaryKey(String areaCode);

    int insert(SArea record);

    int insertSelective(SArea record);

    SArea selectByPrimaryKey(String areaCode);

    SArea selectBySArea(SArea record);

    int updateByPrimaryKeySelective(SArea record);

    int updateByPrimaryKey(SArea record);
}