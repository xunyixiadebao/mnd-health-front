package org.example.djiankang.mis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.djiankang.common.R;
import org.example.djiankang.mis.service.SysRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("misRoleController")
@RequestMapping("/mis/role")
@RequiredArgsConstructor
@Tag(name = "角色管理")
public class SysRoleController {
    private final SysRoleService sysRoleService;

    @GetMapping
    @Operation(summary = "查询所有角色")
    public R list() {
        List<Map<String, Object>> roleList = sysRoleService.listAll();
        return R.ok().put("roleList", roleList);
    }
}