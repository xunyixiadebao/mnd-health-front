package org.example.djiankang.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import org.example.djiankang.db.mapper.SysUserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final SysUserMapper sysUserMapper;

    /**
     * 获取用户的权限
     *
     * @param loginId   账号id （SaToken自动从Redis中获取到，然后给我们传过来了）
     * @param loginType 账号类型
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Integer userId = Convert.toInt(loginId);
        Set<String> permissions = sysUserMapper.selectPermissionsByUserId(userId);
        return new ArrayList<>(permissions);
    }

    /**
     * 获取用户的角色
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return new ArrayList<>();
    }
}