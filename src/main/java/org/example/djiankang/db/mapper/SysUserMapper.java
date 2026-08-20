package org.example.djiankang.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.djiankang.db.entity.SysUserEntity;

import java.util.List;
import java.util.Map;
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
    /**
     * 根据userId查找username
     *
     */
    String findUsernameByUserId(@Param("userId") int userId);

    /**
     * 更新用户的密码
     *
     * @param userId      用户id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 1表示更新成功，其他值表示失败
     */
    int updatePassword(@Param("userId") int userId, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    /**
     * 根据条件分页查询用户信息
     * @param params 查询条件
     * @return 符合条件的结果集
     */
    List<Map<String, Object>> selectPageList(Map<String, Object> params);

    /**
     * 根据查询条件获取符合条件的记录总数
     * @param params 条件
     * @return 记录总数
     */
    long selectPageCount(Map<String, Object> params);

    int insert(SysUserEntity user);
    /**
     * 根据用户名查询用户id
     */
    Integer findUserIdByUsername(@Param("username") String username);
    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    Map<String, Object> selectById(int userId);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 1表示更新成功
     */
    int update(Map<String, Object> user);

    int deleteByIds(Integer[] ids);

    /**
     * 更新用户的状态
     * @param userId 用户id
     * @return 1表示更新成功
     */
    int updateStatus(int userId);
}




