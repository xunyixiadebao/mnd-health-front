package org.example.djiankang.mis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.djiankang.common.PasswordEncryptor;
import org.example.djiankang.db.mapper.SysUserMapper;
import org.example.djiankang.exception.HisException;
import org.example.djiankang.mis.service.SysUserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public Integer login(String username, String password) {
        String encryptPassword = PasswordEncryptor.encryptWithUsernameSalt(username, password);
        Integer userId = sysUserMapper.findUserIdByCredentials(username, encryptPassword);
        if (userId != 1) {
            throw  new HisException("账号或密码错误",401);
        }
        log.info("登陆成功：userId={}",userId);
        return userId;
    }
}
