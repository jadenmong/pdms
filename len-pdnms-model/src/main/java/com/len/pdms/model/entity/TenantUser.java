package com.len.pdms.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "pdms_tenant_user")
public class TenantUser implements Serializable {
    @Id
    @Column(name = "id")
    private String  id;

    @Column(name = "tenant_id")
    private String  tenantId;

    @Column(name = "user_id")
    private String  userId;

    @Column(name = "is_admin")
    private int  isAdmin;

    @Column(name = "create_date")
    private Date createDate;
}
