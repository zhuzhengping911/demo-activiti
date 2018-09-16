package com.zzp.activiti.demo.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/16.
 */
public interface MyCustomMapper {

    @Select("SELECT * FROM ACT_RU_TASK")
    List<Map<String,Object>> findAll();
}
