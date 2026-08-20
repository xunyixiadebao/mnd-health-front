package org.example.djiankang.mis.service;

import java.util.List;
import java.util.Map;

public interface SysDeptService {
    /**
     * 查询部门列表
     * @return 部门列表
     */
    List<Map<String, Object>> listAll();
}
