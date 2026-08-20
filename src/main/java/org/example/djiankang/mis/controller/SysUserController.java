package org.example.djiankang.mis.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.djiankang.common.PageResult;
import org.example.djiankang.common.R;
import org.example.djiankang.db.entity.SysUserEntity;
import org.example.djiankang.mis.controller.from.*;
import org.example.djiankang.mis.service.SysUserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController("misUserController")
@RequestMapping("/mis/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理相关接口")
public class SysUserController {
    private final SysUserService sysUserService;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户名密码登录，返回token和权限列表")
    public R login(@RequestBody @Valid LoginForm form) {
        Integer userId = sysUserService.login(form.getUsername(), form.getPassword());
        // sa-token生成令牌
        StpUtil.login(userId, "WEB");
        // 获取令牌
        String token = StpUtil.getTokenValue();
        // 获取权限列表
        List<String> permissions = StpUtil.getPermissionList();
        return R.ok().put("token", token).put("permissions", permissions);
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "退出系统销毁token")
    @SaCheckLogin // 只有已登录的用户才能退出
    public R logout() {
        // 获取当前登录人的id
        int userId = StpUtil.getLoginIdAsInt();
        // 只让当前登录的人退出（其他端仍然是登录状态）
        StpUtil.logout(userId, "WEB");
        return R.ok("登出成功");
    }
    @PutMapping("/password")
    @Operation(summary = "修改密码")
    @SaCheckLogin
    public R updatePassword(@RequestBody @Valid UpdatePasswordForm form) {
        int userId = StpUtil.getLoginIdAsInt();
        sysUserService.updatePassword(userId, form.getOldPassword(), form.getNewPassword());
        // 修改密码之后退出登录
        StpUtil.logout(userId, "WEB");
        return R.ok("密码修改成功");
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询", description = "根据条件分页查询用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R list(@Valid @ParameterObject UserPageQueryForm form) {
        Integer pageNo = form.getPageNo();
        Integer pageSize = form.getPageSize();
        Integer startIndex = (pageNo - 1) * pageSize;
        Map<String, Object> params = BeanUtil.beanToMap(form);
        params.put("startIndex", startIndex);
        PageResult<Map<String, Object>> pageResult = sysUserService.queryPage(params);
        return R.ok().put("pageResult", pageResult);
    }
    @PostMapping
    @Operation(summary = "保存用户", description = "保存用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)
    public R save(@RequestBody @Valid SaveUserForm form) {
        // 转换
        SysUserEntity user = BeanUtil.toBean(form, SysUserEntity.class);

        // BeanUtil.toBean() 默认不会将"Integer[] roleIds;"转换成json字符串。需要手动转换。
        user.setRoleIds(JSONUtil.toJsonStr(form.getRoleIds()));

        sysUserService.save(user);
        return R.ok("用户信息保存成功");
    }
    @GetMapping("/{userId}")
    @Operation(summary = "根据ID查询用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R getUserById(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable
            @NotNull(message = "userId不能为空")
            @Min(value = 1, message = "userId不能小于1")
            Integer userId) {
        Map<String, Object> user = sysUserService.getById(userId);
        return R.ok().put("user", user);
    }
    @PutMapping
    @Operation(summary = "修改用户信息")
    @SaCheckPermission(value = {"ROOT", "USER:UPDATE"}, mode = SaMode.OR)
    public R update(@RequestBody @Valid UpdateUserForm form) {
        Map<String, Object> user = BeanUtil.beanToMap(form);
        // 将 Integer[] 数组转换为json字符串。
        user.replace("roleIds", JSONUtil.toJsonStr(form.getRoleIds()));
        sysUserService.update(user);
        // 将修改之后的用户从所有设备踢下线，让他重新登录才能拥有新的角色和权限
        StpUtil.logout(form.getUserId());
        return R.ok("用户信息修改成功");
    }
    @Operation(summary = "批量删除用户", description = "根据用户ID数组批量删除用户，并强制踢出已删除用户的登录态")
    @DeleteMapping("/batch")
    @SaCheckPermission(value = {"ROOT", "USER:DELETE"}, mode = SaMode.OR)
    public R remove(@RequestBody @Valid RemoveUserByIdsForm removeUserByIdsForm) {

        Integer currentUserId = StpUtil.getLoginIdAsInt();

        Integer[] userIds = removeUserByIdsForm.getUserIds();

        // 防止删除自己
        if (ArrayUtil.contains(userIds, currentUserId)) {
            return R.error("您不能删除自己");
        }

        // 执行删除
        int rows = sysUserService.removeByIds(userIds);

        // 删除成功则踢出已删除用户
        if (rows > 0) {
            Arrays.stream(userIds).forEach(StpUtil::logout);
        }
        return R.ok().put("rows", rows);
    }
    @Operation(summary = "用户离职", description = "为用户ID办理离职并强制踢出登录态")
    @PutMapping("/{userId}")
    @SaCheckPermission(value = {"ROOT", "USER:UPDATE"}, mode = SaMode.OR)
    public R dismiss(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable
            @NotNull(message = "userId不能为空")
            @Min(value = 1, message = "userId不能小于1")
            Integer userId) {
        sysUserService.dismiss(userId);
        StpUtil.logout(userId);
        return R.ok("离职成功");
    }
}