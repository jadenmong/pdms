package com.len.pdms.web;

import com.len.pdms.model.vo.ProjectVo;
import com.len.pdms.model.vo.TenantVo;
import com.len.pdms.service.ProjectService;
import com.len.util.JsonUtil;
import com.len.util.QuanPrint;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pdms/project")
public class ProjectController {
    @Reference
    private ProjectService projectService;

    @RequestMapping("/showProject")
    public String showProject(String tenantId, Model model){
        QuanPrint.print("ProjectController => showProject() 拿到的租户id为："+tenantId);
        model.addAttribute("tenantId", tenantId);
        return "pdms/project/add_project";
    }
    /**
     * 可以用一个Vo对象来接受请求   /pdms/project/addProject
     * 接收到的值为 tanantId icon 项目名字
     */
    @PostMapping("/addProject")
    @ResponseBody
//    public JsonUtil addProject(String tenantId, String icon, String projectName){
    public JsonUtil addProject(ProjectVo projectVo,Model model){
        // 添加项目到数据库
//        ProjectVo projectVo = new ProjectVo();
//        projectVo.setIcon(icon); projectVo.setProjectName(projectName); projectVo.setTenantId(tenantId);
        QuanPrint.print("添加项目to数据库 ProjectController => addProject");
        QuanPrint.print("ProjectController => addProject"+projectVo);
//        QuanPrint.print("ProjectController => addProject"+tenantId+" ； "+icon+" : "+projectName);
        projectService.addProject(projectVo);
        return JsonUtil.sucess("项目添加成功");
    }

    @RequestMapping("/removeProject")
    @ResponseBody
    public JsonUtil removeProject(ProjectVo projectVo ){
        // 删除项目
        QuanPrint.print("ProjectController =》 removeProject() 要删除的项目id为："+projectVo.getId());
        projectService.removeProject(projectVo);
        return  JsonUtil.sucess("删除项目成功");
    }
}
