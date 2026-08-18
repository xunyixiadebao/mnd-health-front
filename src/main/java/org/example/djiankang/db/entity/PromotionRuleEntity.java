package org.example.djiankang.db.entity;

import lombok.Data;

/**
 * &#064;TableName  promotion_rule
 */
@Data
public class PromotionRuleEntity {
    private Integer ruleId;

    private String ruleName;

    private String ruleContent;

    private String remark;
}