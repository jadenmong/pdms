package com.len.pdms.service.provider.Impl;

import cn.hutool.core.date.DateUtil;
import com.len.base.BaseMapper;
import com.len.base.CurrentUser;
import com.len.base.impl.BaseServiceImpl;
import com.len.pdms.model.entity.Project;
import com.len.pdms.model.vo.ProjectVo;
import com.len.pdms.service.ProjectService;
import com.len.pdms.service.provider.mapper.ProjectMapper;
import com.len.pdms.service.provider.mapper.SysMapper;
import com.len.pdms.service.provider.session.SessionTemplate;
import com.len.util.BeanUtil;
import com.len.util.IDUtil;
import com.len.util.QuanPrint;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务西药注册到dubbo中
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, String> implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    private SessionTemplate sessionTemplate;

    @Override
    public BaseMapper<Project, String> getMappser() {
        return projectMapper ;
    }

    /**
     * 请求会收到一个租户id 图标 项目名称
     * @param projectVo
     * @return
     */
    @Override
    public int addProject(ProjectVo projectVo) {
        Project project = new Project();
        project.setId(IDUtil.getID());
        project.setCreateDate(DateUtil.date());
        CurrentUser id = sessionTemplate.getCurrentUser();
        if( id != null){
            QuanPrint.print(" 不为空");
        project.setCreateUserId(id.getId());
        }
        BeanUtil.copyNotNullBean(projectVo, project);
//        project.setIcon(projectVo.getIcon());
//        project.setProjectId(projectVo.getProjectId());
//        project.setTenantId(projectVo.getTenantId());
        // 创建的用户id为空
//        project.setCreateUserId();
        return this.insertSelective(project);
    }

    /**]
     * 删除项目
     * @param projectVo
     * @return
     */
    @Override
    public int removeProject(ProjectVo projectVo) {

        return this.deleteByPrimaryKey(projectVo.getId());
    }
}
