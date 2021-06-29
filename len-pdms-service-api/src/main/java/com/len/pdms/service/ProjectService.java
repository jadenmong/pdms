package com.len.pdms.service;

import com.len.pdms.model.vo.ProjectVo;

public interface ProjectService {
    // 添加一个项目
    int addProject(ProjectVo projectVo);

    // 删除项目
    int removeProject(ProjectVo projectVo);
}
