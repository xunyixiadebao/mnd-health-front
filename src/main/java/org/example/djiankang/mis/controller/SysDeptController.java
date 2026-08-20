package org.example.djiankang.mis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.djiankang.common.R;
import org.example.djiankang.mis.service.SysDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("misDeptController")
@RequestMapping("/mis/dept")
@RequiredArgsConstructor
@Tag(name = "部门管理")
public class SysDeptController {
    private final SysDeptService sysDeptService;

    @GetMapping
    @Operation(summary = "查询部门列表")
    public R list() {
        List<Map<String, Object>> deptList = sysDeptService.listAll();
        return R.ok().put("deptList", deptList);
    }
}
