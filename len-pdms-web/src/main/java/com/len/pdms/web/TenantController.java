package com.len.pdms.web;
import com.len.util.JsonUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.zookeeper.server.quorum.flexible.QuorumMaj;
import com.len.pdms.model.vo.TenantVo;
import com.len.pdms.service.TenantService;
import com.len.util.QuanPrint;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/pdms/tenant")
public class TenantController {
    //由于这个接口应该取注册中心拿，我已我们需要加远程注解
    @Reference
    TenantService tenantService;
    // 跳转到注册页面
    @GetMapping("/register")
    public String register(){

        return "pdms/tenant_register";
    }

    //注册请求，完成后跳到成功页面
    @RequestMapping("/addTenant")
    public String addTenant(TenantVo tenantVo,Model model){
        //使用的伪代码，来代替用户的注册
        QuanPrint.print("//===========================================");
        QuanPrint.print("//==========使用的伪代码，来代替用户的注册=========");
        QuanPrint.print("//===========================================");
//        TenantVo tenantVo = new TenantVo();
//        tenantVo.setTenantName("quan");tenantVo.setUsername("qingquan");tenantVo.setPassword("123");
        QuanPrint.print(tenantVo.toString());
        QuanPrint.print("TenantController=>addTenant执行完毕");
        tenantService.addTenant(tenantVo);
        model.addAttribute("at_username",tenantVo.getUsername());
        return "pdms/register_success";
    }

    //addTenantUser,返回一个json数据
    @RequestMapping("/addTenantUser")
    @ResponseBody
    public JsonUtil addTenantUser(String username, String tenantId){
        // 添加租户用户,返回添加上时候的状态
        int result = tenantService.addTenantUser(username, tenantId);
        if (result == -1) {
            return  JsonUtil.error("邀请用户不存在");
        }else if (result == -2) {
            return JsonUtil.error("用户已被邀请，不可重复邀请");
        }else {
            return JsonUtil.sucess("邀请用户成功");
        }
    }
    @RequestMapping("/invite/{tenantId}")
    public String invite(@PathVariable("tenantId") String tenantId,Model model){
        System.err.println(tenantId==null);
        model.addAttribute("tenantId",tenantId);
        QuanPrint.print("获取到的tenantId："+tenantId);
        return "pdms/tenant_adduser";
    }
    //跳转到用户邀请界面 request---请求   model----一个域
    @RequestMapping("/test")
    public String test(String tenantId,Model model){
        QuanPrint.print("test成功");
        System.err.println(tenantId==null);
        model.addAttribute("tenantId",tenantId);
        QuanPrint.print("获取到的tenantId："+tenantId);
        return "pdms/tenant_adduser";
    }
    @RequestMapping("/tests")
    @ResponseBody
    public String tests(){

        return "testLoginSuccess changes";
    }
}
