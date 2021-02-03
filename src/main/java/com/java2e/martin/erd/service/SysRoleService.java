package com.java2e.martin.erd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java2e.martin.erd.entity.SysRole;
import org.springframework.transaction.annotation.Transactional;

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
public interface SysRoleService extends IService<SysRole> {


    /**
     * 获取当前用户绑定的角色
     *
     * @return
     */
    Object currentRole();

    /**
     * 保存角色权限
     *
     * @param params
     * @return
     */
    Boolean savePermission(Map params);
}
