package com.java2e.martin.erd.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java2e.martin.erd.entity.SysUser;
import com.java2e.martin.erd.mapper.SysUserMapper;
import com.java2e.martin.erd.service.SysUserService;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public List selectUserRoles(Page page) {
        return baseMapper.selectUserRoles(page);
    }

    @Override
    public Boolean bindRole(Map map) {
        baseMapper.deleteRole(map);
        return baseMapper.bindRole(map);
    }
}
