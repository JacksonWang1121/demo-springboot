package com.example.demospringboot.mapper;

import com.example.demospringboot.entity.SBaffleConfig;

import java.util.List;

public interface SBaffleConfigMapper {
    int deleteByPrimaryKey(String interfaceId);

    int insert(SBaffleConfig record);

    int insertSelective(SBaffleConfig record);

    SBaffleConfig selectByPrimaryKey(String interfaceId);

    List<SBaffleConfig> selectAll();

    int updateByPrimaryKeySelective(SBaffleConfig record);

    int updateByPrimaryKey(SBaffleConfig record);
}