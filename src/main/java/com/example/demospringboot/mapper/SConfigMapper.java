package com.example.demospringboot.mapper;

import com.example.demospringboot.entity.SConfig;

import java.util.List;

public interface SConfigMapper {
    int deleteByPrimaryKey(Integer cfgId);

    int insert(SConfig record);

    int insertSelective(SConfig record);

    List<SConfig> selectAll(SConfig record);

    SConfig selectByPrimaryKey(Integer cfgId);

    SConfig selectByCfgName(String cfgName);

    int updateByPrimaryKeySelective(SConfig record);

    int updateByPrimaryKey(SConfig record);
}