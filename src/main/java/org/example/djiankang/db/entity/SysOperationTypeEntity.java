package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  sys_operation_type
 */
@Data
public class SysOperationTypeEntity {
    private Integer id;

    private String operationCode;

    private String operationName;
}