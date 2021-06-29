package com.len.pdms.web;


import com.len.pdms.model.vo.TenantVo;
import com.len.pdms.service.TenantService;
import com.len.util.QuanPrint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pdms/tenant")
public class TenantController {
    //由于这个接口应该取注册中心拿，我已我们需要加远程注解
    TenantService tenantService;
    // 跳转到注册页面
    @GetMapping("/register")
    public String register(){

        return "pdms/tenant_register";
    }

    //注册请求，完成后跳到成功页面
    @RequestMapping("/addTenant")
    public String addTenant(TenantVo tenantVo){
        tenantService.addTenant(tenantVo);
        QuanPrint.print("TenantController=>addTenant执行完毕");
        return "pdms/tenant_register-success";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        QuanPrint.print("test成功");
        return "test成功";
    }
}
