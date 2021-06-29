package com.len.pdms.service.provider.mapper;

import com.len.base.BaseMapper;
import com.len.pdms.model.entity.TenantUser;
import org.apache.ibatis.annotations.Mapper;

import javax.websocket.server.PathParam;

@Mapper
public interface TenantuserMapper extends BaseMapper<TenantUser,String> {
    // 查询租户下是否已经有这个用户了
    int check(@PathParam("userId") String userId,@PathParam("tenantId") String tenantId);
}
