package com.java2e.martin.erd.service.impl;

import com.java2e.martin.erd.entity.SysPermission;
import com.java2e.martin.erd.mapper.SysPermissionMapper;
import com.java2e.martin.erd.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shishao
 * @version 1.0
 * @date 2020-11-10
 * @describtion
 * @since 1.0
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List allPermissions(Map map) {
        return baseMapper.allPermissions(map);
    }

    @Override
    public List checkedPermissions(Map map) {
        return baseMapper.checkedPermissions(map);
    }
}
