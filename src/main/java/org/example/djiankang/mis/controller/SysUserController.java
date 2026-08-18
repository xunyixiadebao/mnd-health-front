package org.example.djiankang.mis.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.djiankang.common.R;
import org.example.djiankang.mis.controller.from.LoginForm;
import org.example.djiankang.mis.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}