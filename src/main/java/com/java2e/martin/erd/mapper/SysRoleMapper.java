package com.java2e.martin.erd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java2e.martin.erd.entity.SysRole;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author shishao
 * @version 1.0
 * @date 2020-11-10
 * @describtion
 * @since 1.0
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询当前用户的角色信息
     *
     * @param username
     * @return
     */
    Object currentRole(String username);

    /**
     * 删除角色的权限
     *
     * @param roleId
     */
    void deletePermissionByRole(String roleId);

    /**
     * 保存新权限
     *
     * @param checkedPermissions
     * @return
     */
    Boolean insertRolePermission(Map checkedPermissions);
}
