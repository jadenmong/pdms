package com.len.controller;

import com.len.core.annotation.Log;
import com.len.core.shiro.Principal;
import com.len.entity.SysUser;
import com.len.pdms.model.entity.Project;
import com.len.pdms.model.entity.ProjectTemp;
import com.len.service.SysUserService;
import com.len.util.CustomUsernamePasswordToken;
import com.len.util.QuanPrint;
import com.len.util.VerifyCodeUtils;
import com.mysql.jdbc.StringUtils;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaomeng
 * @date 2017/12/4.
 * @email 154040976@qq.com
 * 登录、退出页面
 */
@Controller
@Slf4j
@Api(value = "登录业务",description="登录校验处理")
public class LoginController {

    @Autowired
    SysUserService userService;
    private static final String CODE_ERROR = "code.error";

    @GetMapping(value = "")
    public String loginInit() {
        return loginCheck();
    }

    @GetMapping(value = "/pdms/tenant/goLogin/{username}")
    public String goLogin(@PathVariable("username") String username, Model model) {
        QuanPrint.print("回显用户名为: "+username);
        // 传递username
        model.addAttribute("gl_username",username);
        Subject sub = SecurityUtils.getSubject();
        if (sub.isAuthenticated()) {
            return "/main/main";
        } else {
            model.addAttribute("message", "请重新登录");
            return "/login";
        }
    }

    @GetMapping(value = "/login")
    public String loginCheck() {
        Subject sub = SecurityUtils.getSubject();
        Boolean flag2 = sub.isRemembered();
        boolean flag = sub.isAuthenticated() || flag2;
        if (flag) {
            return "/main/main";
        }
        return "/login";
    }

    /**
     * 登录动作
     *
     * @param user
     * @param model
     * @param rememberMe
     * @return
     */
    @ApiOperation(value = "/login", httpMethod = "POST", notes = "登录method")
    @PostMapping(value = "/login")
    public String login(SysUser user, Model model, String rememberMe, HttpServletRequest request) {
        // 验证码判断
        String codeMsg = (String) request.getAttribute("shiroLoginFailure");
        if (CODE_ERROR.equals(codeMsg)) {
            model.addAttribute("message", "验证码错误");
            return "/login";
        }
        CustomUsernamePasswordToken token = new CustomUsernamePasswordToken(user.getUsername().trim(),
                user.getPassword(), "UserLogin");
        Subject subject = Principal.getSubject();
        String msg = null;
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                token.getUsername();
                return "redirect:/main";
            }
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            msg = "用户名/密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败多次，账户锁定10分钟";
        }
        if (msg != null) {
            model.addAttribute("message", msg);
        }
        return "/login";
    }

    @GetMapping("/main")
    public String main(HttpServletRequest request) {
        String tenantId = (String)request.getParameter("tenantId");
        String projectId = (String)request.getParameter("project_id");
        String projectName = (String)request.getParameter("project_name");
        QuanPrint.print("LoginController => main() 获取到的参数为：tenantId="+tenantId+" projectId="+projectId+" projectName="+projectName);
        // 判断显示什么页面
        if (StringUtils.isNullOrEmpty(tenantId)) {
            QuanPrint.print("进入管理页面");
            // 使用projectMap
            Map<String,List<ProjectTemp>> projectMap = new HashMap<>();
            // 从session中获取登录的租户
            List<Map> tenants = (List<Map> )Principal.getSession().getAttribute("tenants");
            // 拿到租户的所有项目
            for (Map tenant : tenants) {
                // 获取租户id
                String id = (String)tenant.get("id");
                // 根据用户的id取查询对应的project
                List<ProjectTemp> projects = userService.getProject(id);
                projectMap.put(id,projects);
            }
            request.setAttribute("showTenant",true);
            request.setAttribute("projectMap",projectMap);
        }else {
            Principal.getSession().setAttribute("globalProjectId",projectId);
            Principal.getSession().setAttribute("globalTenantId",tenantId);
            QuanPrint.print("进入项目页面");
            request.setAttribute("showTenant", false);
        }
        return "main/main";
    }

    @Log(desc = "用户退出平台")
    @GetMapping(value = "/logout")
    public String logout() {
        Subject sub = SecurityUtils.getSubject();
        sub.logout();
        return "/login";
    }


    @GetMapping(value = "/getCode")
    public void getYzm(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            log.info("verifyCode:{}", verifyCode);
            //存入会话session
            HttpSession session = request.getSession(true);
            session.setAttribute("_code", verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
