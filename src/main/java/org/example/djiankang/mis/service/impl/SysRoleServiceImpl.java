package org.example.djiankang.mis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.djiankang.db.mapper.SysRoleMapper;
import org.example.djiankang.mis.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {


    private final SysRoleMapper sysRoleMapper;

    @Override
    public List<Map<String, Object>> listAll() {
        return sysRoleMapper.selectAll();
    }
}