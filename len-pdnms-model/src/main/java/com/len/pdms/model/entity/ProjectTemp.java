package com.len.pdms.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "pdms_project")
public class ProjectTemp implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "tenant_id")
    private String tenant_id;
    @Column(name = "project_name")
    private String project_name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "create_date")
    private Date   create_date;

    @Column(name = "create_user_id")
    private String create_user_id;

}
