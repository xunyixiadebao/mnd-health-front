package org.example.djiankang.mis.service;

public interface SysUserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回null时登录失败，返回非null时登录成功
     */
    Integer login(String username, String password);
}
