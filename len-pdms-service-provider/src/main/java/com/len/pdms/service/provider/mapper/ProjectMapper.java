package com.len.pdms.service.provider.mapper;

import com.len.base.BaseMapper;
import com.len.pdms.model.entity.Project;
import com.len.pdms.model.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseMapper<Project,String> {
}
