package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  med_appointment_limit
 */
@Data
public class MedAppointmentLimitEntity {
    private Integer id;

    private String appointmentDate;

    private Integer actualLimit;

    private Integer maxLimit;

    private Integer actualCount;

    private String remark;

    private String createTime;
}