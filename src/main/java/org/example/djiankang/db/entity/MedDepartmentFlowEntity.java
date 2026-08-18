package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  med_department_flow
 */
@Data
public class MedDepartmentFlowEntity {
    private Integer id;

    private String departmentName;

    private Integer currentCount;

    private Integer maxCapacity;

    private Integer weight;

    private Integer priority;

    private String beaconUuid;
}