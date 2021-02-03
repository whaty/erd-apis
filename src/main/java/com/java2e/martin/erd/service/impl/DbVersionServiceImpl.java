package com.java2e.martin.erd.service.impl;

import com.java2e.martin.erd.entity.DbVersion;
import com.java2e.martin.erd.mapper.DbVersionMapper;
import com.java2e.martin.erd.service.DbVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 狮少
 * @since 2020-10-29
 */
@Service
public class DbVersionServiceImpl extends ServiceImpl<DbVersionMapper, DbVersion> implements DbVersionService {

    @Override
    public String dbversion(String projectId) {
        return baseMapper.dbversion(projectId);
    }

    @Override
    public Integer rebaseline(String  projectId) {
        return baseMapper.rebaseline(projectId);
    }
}
