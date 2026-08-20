package org.example.djiankang.db.mapper;

import java.util.List;
import java.util.Map;

/**
* @author zl
* &#064;description  针对表【sys_department(部门信息表)】的数据库操作Mapper
* &#064;createDate  2026-08-17 20:34:37
* &#064;Entity  org.example.djiankang.db.entity.SysDepartmentEntity
 */
public interface SysDepartmentMapper {
    /**
     * 查询所有部门
     *
     * @return 部门列表
     */
    List<Map<String, Object>> selectAll();

}




