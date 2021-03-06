package com.java2e.martin.erd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.erd.entity.SysUser;

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
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysUser findByUserName(String username);

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

    /**
     * 删除角色
     *
     * @param map
     */
    void deleteRole(Map map);
}
