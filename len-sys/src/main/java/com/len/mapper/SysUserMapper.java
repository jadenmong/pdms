package com.len.mapper;

import com.len.entity.SysUser;
import com.len.pdms.model.entity.Project;
import com.len.pdms.model.entity.ProjectTemp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends com.len.base.BaseMapper<SysUser,String> {

    SysUser login(@Param("username") String username);

    int count();

    int add(SysUser user);

    int delById(String id);

    int checkUser(String username);

    /**
     * 更新密码
     * @param user
     * @return
     */
    int rePass(SysUser user);

    List<SysUser> getUserByRoleId(@Param("roleId")String roleId);

    // 获取用户的租户信息   非管理员用户
    List<Map> getUserTenants(@Param("userId") String id);

    //管理员用户
    List<Map> getTenants();

    //通过id查询项目
    List<ProjectTemp> getProject(@Param("id") String id);
}