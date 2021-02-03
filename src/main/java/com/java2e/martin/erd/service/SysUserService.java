package com.java2e.martin.erd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.erd.entity.SysUser;
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
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询用户角色
     *
     * @return
     * @param page
     */
    List selectUserRoles(Page page);

    /**
     * 用户绑定角色
     *
     * @param map
     * @return
     */
    Boolean bindRole(Map map);
}
