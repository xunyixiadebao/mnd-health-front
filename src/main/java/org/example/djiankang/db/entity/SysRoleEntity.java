package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  sys_role
 */
@Data
public class SysRoleEntity {
    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private String permissionIds;

    private String defaultPermissions;

    private Integer isSystem;
}