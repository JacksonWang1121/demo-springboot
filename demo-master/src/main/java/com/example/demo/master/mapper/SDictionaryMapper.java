package com.example.demo.master.mapper;

import com.example.demo.master.entity.SDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SDictionaryMapper {
    int deleteByPrimaryKey(@Param("dictType") String dictType, @Param("dictCode") String dictCode);

    int insert(SDictionary record);

    int insertSelective(SDictionary record);

    List<SDictionary> selectAllToDictType(SDictionary record);

    List<SDictionary> selectAll(SDictionary record);

    SDictionary selectByPrimaryKey(@Param("dictType") String dictType, @Param("dictCode") String dictCode);

    int updateByPrimaryKeySelective(SDictionary record);

    int updateByPrimaryKey(SDictionary record);
}