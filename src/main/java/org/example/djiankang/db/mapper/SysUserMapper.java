package org.example.djiankang.db.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
* @author zl
* &#064;description  针对表【sys_user(系统用户表)】的数据库操作Mapper
* &#064;createDate  2026-08-17 20:34:37
* &#064;Entity  org.example.djiankang.db.entity.SysUserEntity
 */
public interface SysUserMapper {
    Set<String> selectPermissionsByUserId(int userId);

    /**
     * 根据用户名和密码验证登录，登录成功返回userId，登录失败返回null
     */
    Integer findUserIdByCredentials(@Param("username") String username, @Param("password") String password);
}




