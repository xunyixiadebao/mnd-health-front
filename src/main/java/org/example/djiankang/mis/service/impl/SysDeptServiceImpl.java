package org.example.djiankang.mis.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.djiankang.db.mapper.SysDepartmentMapper;
import org.example.djiankang.mis.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDepartmentMapper sysDepartmentMapper;

    @Override
    public List<Map<String, Object>> listAll() {
        return sysDepartmentMapper.selectAll();
    }
}