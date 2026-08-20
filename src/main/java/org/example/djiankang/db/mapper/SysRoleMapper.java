package org.example.djiankang.db.mapper;

import java.util.List;
import java.util.Map;

/**
* @author zl
* @description 针对表【sys_role(系统角色表)】的数据库操作Mapper
* @createDate 2026-08-17 20:34:37
* @Entity org.example.djiankang.db.entity.SysRoleEntity
*/
public interface SysRoleMapper {
    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Map<String, Object>> selectAll();
}




