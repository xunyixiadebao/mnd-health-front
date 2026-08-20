package org.example.djiankang.mis.service;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<Map<String, Object>> listAll();
}
