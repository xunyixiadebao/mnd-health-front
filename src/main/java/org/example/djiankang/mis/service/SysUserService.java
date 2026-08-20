package org.example.djiankang.mis.service;

import org.example.djiankang.common.PageResult;
import org.example.djiankang.db.entity.SysUserEntity;

import java.util.Map;

public interface SysUserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回null时登录失败，返回非null时登录成功
     */
    Integer login(String username, String password);
    /**
     * 修改密码
     * @param userId 用户id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void updatePassword(int userId, String oldPassword, String newPassword);

    /**
     * 分页查询用户信息
     * @param params 查询条件
     * @return 分页对象
     */
    PageResult<Map<String, Object>> queryPage(Map<String, Object> params);

    /**
     * 保存用户信息
     * @param user 用户信息
     */
    void save(SysUserEntity user);

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    Map<String, Object> getById(int userId);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    void update(Map<String, Object> user);

    int removeByIds(Integer[] ids);

    /**
     * 为用户办理离职
     *
     * @param userId 用户id
     */
    void dismiss(int userId);



}
