package com.len.pdms.service.provider.Impl;
//import com.codahale.metrics.Reservoir;
import cn.hutool.core.date.DateUtil;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.pdms.model.entity.TenantUser;
import com.len.pdms.model.vo.TenantVo;
import com.len.pdms.service.TenantService;
import com.len.pdms.service.provider.mapper.TenantMapper;
import com.len.pdms.service.provider.mapper.SysMapper;
import com.len.pdms.service.provider.mapper.TenantuserMapper;
import com.len.util.BeanUtil;
import com.len.util.IDUtil;
import com.len.util.Md5Util;
import com.len.util.QuanPrint;
import io.netty.util.internal.StringUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.len.pdms.model.entity.Tenant;

import java.util.HashMap;
import java.util.Map;


@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant,String> implements TenantService {
    @Autowired
    TenantMapper tenantMapper;
    @Autowired
    SysMapper sysMapper;
    @Autowired
    TenantuserMapper tenantuserMapper;
    // 为了拿到子的mapper
    @Override
    public BaseMapper getMappser() {
        return tenantMapper;
    }


    // 提供服务，增加一个租户
    //租户从前端过来：租户名+租户管理员的用户名和密码
    @Override
    public int addTenant(TenantVo tenantVo){
        //======================================================================
        //                              插入租户
        //======================================================================
            Tenant tenant = new Tenant();
            // 将前端获取到的用户拷贝到自己创建的用户
            BeanUtil.copyNotNullBean(tenantVo, tenant);// 接受前端对象
            //时间和uuid问题
            tenant.setTenantName(tenantVo.getTenantName());
            tenant.setCreateDate(DateUtil.date());
            tenant.setId(IDUtil.getID());

            //调用dao层处理对像
            System.out.println("调用dao层处理对像"+tenant.toString());
            int status = this.insertSelective(tenant); //来自继承的BaseServiceImpl

            QuanPrint.print("TenantServiceiMpl=>addTenant:插入租户完毕");
        //======================================================================
        //                              插入用户
        //======================================================================
                // 给数据库添加一个用户Mapper和Mapper.xml
            Map map = new HashMap<>();
            map.put("id",tenant.getId());
            map.put("username",tenantVo.getUsername());
            // 使用md5加密
            map.put("password",Md5Util.getMD5(tenantVo.getPassword(),tenantVo.getUsername().trim()));
            map.put("create_date",tenant.getCreateDate());
            System.err.println("插入用户的信息为："+map.toString());
            sysMapper.addUser(map);
            QuanPrint.print("TenantServiceiMpl=>addTenant:插入用户完毕");
        //======================================================================
        //                              插入用户租户
        //======================================================================
                // 给表建立对应的实体类
            TenantUser user = new TenantUser();
            user.setId(IDUtil.getID());           // 租户用户id
            user.setTenantId(tenant.getId());     // 用户id和租户id一样，是自己
            user.setIsAdmin(1);                   // 为 1 就是管理员
            user.setUserId(tenant.getId());       //
            user.setCreateDate(tenant.getCreateDate());   //
            tenantuserMapper.insert(user);
            QuanPrint.print("TenantServiceiMpl=>addTenant:插入用户租户完毕");
        //======================================================================
        //                              插入用户角色表
        //======================================================================
            Map userRole =  new HashMap();
            userRole.put("user_id",tenant.getId());
            userRole.put("role_id","fb483b76457811e8bcf1309c2315f9aa"); //角色复制默认他是租户
            sysMapper.addUserRole(userRole);
            QuanPrint.print("TenantServiceiMpl=>addTenant:插入用户角色完毕");

            return status;
    }

    // 添加租户用户
    @Override
    public int addTenantUser(String username, String tenantId) {
        QuanPrint.print("TenantServiceImpl=>addTenantUser()拿到的参数为:"+username+" ; "+tenantId);
        // 判断用户是否存在
        String id = "";
        String user_name = "xiaohwi";
        if(username.equals("") || username.length() ==0){
             id = sysMapper.getUserId(user_name);
        }else {
             id = sysMapper.getUserId(username);
        }
        if (StringUtil.isNullOrEmpty(id)) {  // 用户不存在
            return -1;
        }
        // 判断这个用户在用户中已经加入
        // 思路： 检查这个用户在租户中是否存在
        int check = tenantuserMapper.check(id, tenantId);
        if(check > 0) {
            return -2; //用户已经在这个租户下面，不可以重复邀请
        }
        // 可以加入
        //======================================================================
        //                              插入用户租户
        //======================================================================
        // 给表建立对应的实体类
        TenantUser user = new TenantUser();
        user.setId(IDUtil.getID());           // 租户用户id
        user.setTenantId(tenantId);     // 用户id和租户id一样，是自己
        user.setIsAdmin(0);                   // 为 1 就是管理员
        user.setUserId(id);       //
        user.setCreateDate(DateUtil.date());   //
        tenantuserMapper.insert(user);
        QuanPrint.print("TenantServiceImpl=>addTenantUser:租户邀请添加成功");

        return 0;
    }


}
