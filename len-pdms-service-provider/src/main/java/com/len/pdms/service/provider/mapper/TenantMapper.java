package com.len.pdms.service.provider.mapper;


import com.len.base.BaseMapper;
import com.len.pdms.model.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;

// 使用通用mapper
// 会自动帮 tenant这个实体类编写增删改查等基础方法
@Mapper
public interface TenantMapper extends BaseMapper<Tenant,String> {
}
