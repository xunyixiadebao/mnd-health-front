package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  sys_permission
 */
@Data
public class SysPermissionEntity {
    private Integer permissionId;

    private String permissionCode;

    private Integer moduleId;

    private Integer actionId;
}