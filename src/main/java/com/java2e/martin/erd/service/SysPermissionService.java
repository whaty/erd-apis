package com.java2e.martin.erd.service;

import com.java2e.martin.erd.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shishao
 * @version 1.0
 * @date 2020-11-10
 * @describtion
 * @since 1.0
 */
@Transactional(rollbackFor = Exception.class)
public interface SysPermissionService extends IService<SysPermission> {

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
