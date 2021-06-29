package com.len.pdms.service.provider.mapper;

import com.len.pdms.model.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface SysMapper {
    //插入一个用户
    void addUser (Map map);
    // 插入一个租户角色
    void addUserRole(Map map);
    // 根据用户名获取id
    String getUserId(@Param("username") String username);

}
