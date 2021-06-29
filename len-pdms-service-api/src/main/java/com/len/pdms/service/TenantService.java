package com.len.pdms.service;

import com.len.base.BaseMapper;
import com.len.pdms.model.entity.Tenant;
import com.len.pdms.model.vo.TenantVo;

import javax.websocket.server.PathParam;

public interface TenantService extends BaseMapper<Tenant,String>{
    // 添加一个租户，参数从前端户获取
    public int addTenant(TenantVo tenantVo);
    // 添加租户 === 用户
    public int addTenantUser(String username,  String tenantId);
}
