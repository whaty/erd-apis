package com.java2e.martin.erd.service.impl;

import com.java2e.martin.erd.config.SecurityContextUtil;
import com.java2e.martin.erd.entity.SysRole;
import com.java2e.martin.erd.mapper.SysRoleMapper;
import com.java2e.martin.erd.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shishao
 * @version 1.0
 * @date 2020-11-10
 * @describtion
 * @since 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Object currentRole() {
        String username = SecurityContextUtil.getUser();
        return baseMapper.currentRole(username);
    }

    @Override
    public Boolean savePermission(Map params) {
        String roleId = (String) params.get("roleId");
        baseMapper.deletePermissionByRole(roleId);
        return baseMapper.insertRolePermission(params);
    }
}
