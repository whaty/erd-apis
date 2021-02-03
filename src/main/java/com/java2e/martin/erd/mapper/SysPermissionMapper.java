package com.java2e.martin.erd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java2e.martin.erd.entity.SysPermission;

import java.util.List;
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
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 根据用户id获取权限
     *
     * @param userId
     * @return
     */
    List<SysPermission> findByAdminUserId(String userId);

    /**
     * 查询所有权限
     *
     * @return
     */
    List<SysPermission> findAll();

    /**
     * 获取所有权限
     *
     * @param map
     * @return
     */
    List allPermissions(Map map);

    /**
     * 获取当前角色已分配权限
     *
     * @param map
     * @return
     */
    List checkedPermissions(Map map);
}
