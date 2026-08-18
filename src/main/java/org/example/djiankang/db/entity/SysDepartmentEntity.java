package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  sys_department
 */
@Data
public class SysDepartmentEntity {
    private Integer id;

    private String deptName;

    private String telephone;

    private String email;

    private String remark;
}