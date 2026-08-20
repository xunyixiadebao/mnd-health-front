package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  crm_customer_location
 */
@Data
public class CrmCustomerLocationEntity {
    private Integer id;

    private Integer customerId;

    private String beaconUuid;

    private Integer departmentId;

    private String createTime;
}