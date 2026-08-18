package org.example.djiankang.db.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * &#064;TableName  med_exam_package
 */
@Data
public class MedExamPackageEntity {
    private Integer id;

    private String packageCode;

    private String packageName;

    private String description;

    private String departmentExam;

    private String labExam;

    private String medicalExam;

    private String otherExam;

    private String examItems;

    private String coverImage;

    private BigDecimal originalPrice;

    private BigDecimal currentPrice;

    private Integer salesVolume;

    private String packageType;

    private String tags;

    private String categoryId;

    private String promotionId;

    private Integer status;

    private String md5Hash;

    private String updateTime;

    private String createTime;
}