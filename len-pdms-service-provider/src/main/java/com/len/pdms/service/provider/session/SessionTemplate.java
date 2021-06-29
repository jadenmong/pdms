package com.len.pdms.service.provider.session;

import com.len.base.CurrentUser;
import com.len.util.QuanPrint;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Component
public class SessionTemplate {
    @Autowired
    private RedisTemplate redisTemplate;

    //获取session
    public Session getSession(){
        String sessionId = RpcContext.getContext().getAttachment("sessionId");
        QuanPrint.print("Rpc获取到的sessionid为： "+sessionId);
        // 获取key
        String key = "shiro-activeSessionCache:" + sessionId;
        QuanPrint.print("key:"+ key.getBytes());
        Session o = (Session)redisTemplate.opsForValue().get(key.getBytes());
        return o;
    }

    //获取sessionId
    public String getSessionId(){
        return getSession().getId().toString();
    }

    //获取session中的用户
    public CurrentUser getCurrentUser(){
        if (getSession() != null) {
            CurrentUser currentPrincipal = (CurrentUser) getSession().getAttribute("currentPrincipal");
            QuanPrint.print("SessionTemplate => getCurrentUser() 获取到的是否为空" + currentPrincipal);
            return currentPrincipal;
        }
        QuanPrint.print("SessionTemplate => getCurrentUser() 为空");
        return null;
    }

    //拿到sessison中的全局项目id
    public String getGlobalProjectId(){
        if (getSession() != null) {
        String globalProjectId = (String)getSession().getAttribute("globalProjectId");
        QuanPrint.print("SessionTemplate => getGlobalProjectId() 获取到的projectId为 "+ globalProjectId);
        return globalProjectId;
        }
        QuanPrint.print("SessionTemplate => getGlobalProjectId() 为空");
        return null;
    }

    //拿到sesssion中的全局租户id
    public String getGlobalTenantId(){
        String globalTenantId = (String)getSession().getAttribute("globalTenantId");
        return globalTenantId;
    }
}
