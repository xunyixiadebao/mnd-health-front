package org.example.djiankang.mis.controller.from;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Schema(description = "用户分页查询请求参数")
public class UserPageQueryForm {

    @Schema(description = "页码", example = "1")
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNo = 1; // 不会为空，因为有默认值

    @Schema(description = "每页条数", example = "10")
    @Range(min = 10, max = 50, message = "每页条数必须在10~50之间")
    private Integer pageSize = 10; // 不会为空，因为有默认值

    @Schema(description = "真实姓名（中文）", example = "张三")
    @Pattern(regexp = "^$|^[\\u4e00-\\u9fa5]{1,10}$", message = "realName内容不正确")
    private String realName; // 允许为null，允许为空字符串

    @Schema(description = "性别", allowableValues = {"男", "女"}, example = "男")
    @Pattern(regexp = "^男$|^女$", message = "性别错误：只能为'男'或'女'")
    private String gender; // 允许为null

    @Schema(description = "角色ID", example = "1")
    @Min(value = 1, message = "角色ID不能小于1")
    private Integer roleId; // 允许为null

    @Schema(description = "部门ID", example = "1")
    @Min(value = 1, message = "部门ID不能小于1")
    private Integer deptId; // 允许为null

    @Schema(description = "用户状态", allowableValues = {"1", "2"}, example = "1")
    @Range(min = 1, max = 2, message = "用户状态错误：1-正常，2-停用")
    private Integer userStatus; // 允许为null
}