package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  crm_customer_im
 */
@Data
public class CrmCustomerImEntity {
    private Integer id;

    private Integer customerId;

    private String lastLoginTime;
}