package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  sys_config
 */
@Data
public class SysConfigEntity {
    private Integer configId;

    private String configKey;

    private String configValue;

    private String remark;
}