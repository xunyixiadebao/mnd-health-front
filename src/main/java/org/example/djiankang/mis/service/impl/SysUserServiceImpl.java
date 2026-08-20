package org.example.djiankang.mis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.djiankang.common.PageResult;
import org.example.djiankang.common.PasswordEncryptor;
import org.example.djiankang.db.entity.SysUserEntity;
import org.example.djiankang.db.mapper.SysUserMapper;
import org.example.djiankang.exception.HisException;
import org.example.djiankang.mis.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public Integer login(String username, String password) {
        String encryptPassword = PasswordEncryptor.encryptWithUsernameSalt(username, password);
        Integer userId = sysUserMapper.findUserIdByCredentials(username, encryptPassword);
        if (userId == null) {
            throw  new HisException("账号或密码错误",401);
        }
        log.info("登陆成功：userId={}",userId);
        return userId;
    }

    @Override
    public void updatePassword(int userId, String oldPassword, String newPassword) {
        String username = sysUserMapper.findUsernameByUserId(userId);
        if (username == null) {
            throw new HisException("用户不存在",404);
        }
        String encryptOldPassword = PasswordEncryptor.encryptWithUsernameSalt(username, oldPassword);
        String encryptNewPassword = PasswordEncryptor.encryptWithUsernameSalt(username, newPassword);
        int rows = sysUserMapper.updatePassword(userId, encryptOldPassword, encryptNewPassword);
        if (rows != 1) {
            throw new HisException("修改密码失败",400);
        }
        log.info("密码修改成功：userId={}", userId);
    }

    @Override
    public PageResult<Map<String, Object>> queryPage(Map<String, Object> params) {
        long count = sysUserMapper.selectPageCount(params);
        List<Map<String, Object>> records = new ArrayList<>();
        if (count > 0) {
            records = sysUserMapper.selectPageList(params);
        }
        return PageResult.of(records, count);
    }

    @Override
    @Transactional
    public void save(SysUserEntity user) {
        Integer userId = sysUserMapper.findUserIdByUsername(user.getUsername());
        if (userId != null) {
            throw new HisException("用户名已存在", 400);
        }
        String password = PasswordEncryptor.encryptWithUsernameSalt(user.getUsername(), user.getPassword());
        user.setPassword(password);
        int rows = sysUserMapper.insert(user);
        if (rows != 1) {
            throw new HisException("用户信息保存失败", 500);
        }
        log.info("用户信息保存成功：username={}", user.getUsername());
    }

    @Override
    public Map<String, Object> getById(int userId) {

        Map<String, Object> user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new HisException("用户信息不存在", 404);
        }
        return user;
    }

    @Override
    @Transactional
    public void update(Map<String, Object> user) {
        int rows = sysUserMapper.update(user);
        if (rows != 1) {
            throw new HisException("用户信息更新失败", 400);
        }
        log.info("用户信息更新成功：userId={}", user.get("userId"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Integer[] ids) {
        return sysUserMapper.deleteByIds(ids);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dismiss(int userId) {
        int rows = sysUserMapper.updateStatus(userId);
        if (rows != 1) {
            throw new HisException("员工办理离职失败", 400);
        }
        log.info("员工已成功办理离职：userId={}", userId);
    }
}
