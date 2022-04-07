package com.example.kruscaloracle.dao;

import com.example.kruscaloracle.pojo.Edata;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource
public interface EdataMapper {
    List<Edata> listEdata();
}
