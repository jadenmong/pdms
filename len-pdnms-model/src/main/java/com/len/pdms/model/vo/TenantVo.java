package com.len.pdms.model.vo;

import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;


public class TenantVo implements Serializable {

    private String id; //uuid
    private String tenantName; //租户这个公司的名字
    private Date createDate; // 创建的日期

    // 租公司管理员的账户和密码
    private String username;
    private String password;

    public TenantVo() {
    }

    public TenantVo(String id, String tenantName, Date createDate, String username, String password) {
        this.id = id;
        this.tenantName = tenantName;
        this.createDate = createDate;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TenantVo{" +
                "id='" + id + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", createDate=" + createDate +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
