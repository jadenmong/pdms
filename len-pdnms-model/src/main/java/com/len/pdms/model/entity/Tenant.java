package com.len.pdms.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Table(name = "pdms_tenant")
public class Tenant implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "tenant_name")
    private String tenantName;
    @Column(name = "create_date")
    private Date createDate;
}
