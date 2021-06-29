package com.len.pdms.model.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectVo implements Serializable {

    private String id;

    private String tenantId;

    private String projectName;

    private String icon;

    private Date createDate;

    private String createUserId;
}
