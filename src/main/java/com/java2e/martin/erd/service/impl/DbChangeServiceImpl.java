package com.java2e.martin.erd.service.impl;

import cn.fisok.pdman.command.ExecResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java2e.martin.erd.entity.DbChange;
import com.java2e.martin.erd.mapper.DbChangeMapper;
import com.java2e.martin.erd.service.DbChangeService;
import com.java2e.martin.erd.util.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 版本表 服务实现类
 * </p>
 *
 * @author 狮少
 * @since 2020-10-28
 */
@Service
public class DbChangeServiceImpl extends ServiceImpl<DbChangeMapper, DbChange> implements DbChangeService {

    @Override
    public ExecResult loadHistory(String projectId) {
        QueryWrapper<DbChange> wrapper = new QueryWrapper<>();
        wrapper.eq("projectId", projectId);
        wrapper.orderBy(false, false, "version");
        List<DbChange> dbChanges = this.list(wrapper);
        return getHashMapsByDbChanges(dbChanges);
    }

    @Override
    public ExecResult getHashMapsByDbChanges(List<DbChange> dbChanges) {
        ExecResult ret = new ExecResult();
        List<HashMap<Object, Object>> collect = dbChanges.stream().map(dv1 -> {
            HashMap<Object, Object> hashMap = new HashMap<>();
            try {
                hashMap.put("id", dv1.getId());
                hashMap.put("baseVersion", dv1.getBaseVersion());
                hashMap.put("changes", JsonUtil.parse(new String(dv1.getChanges()), List.class));
                hashMap.put("projectJSON", JsonUtil.parse(new String(dv1.getProjectJSON()), Map.class));
                hashMap.put("projectId", dv1.getProjectId());
                hashMap.put("version", dv1.getVersion());
                hashMap.put("versionDate", dv1.getVersionDate());
                hashMap.put("versionDesc", dv1.getVersionDesc());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return hashMap;
        }).collect(Collectors.toList());
        ret.setBody(collect);
        ret.setStatus("SUCCESS");
        return ret;
    }

    @Override
    public ExecResult deleteHistory(String changeId) {
        ExecResult ret = new ExecResult();
        ret.setBody(this.removeById(changeId));
        ret.setStatus("SUCCESS");
        return ret;
    }

    @Override
    public ExecResult deleteAllHistory(String projectId) {
        ExecResult ret = new ExecResult();
        QueryWrapper<DbChange> wrapper = new QueryWrapper<>();
        wrapper.eq("projectId", projectId);
        ret.setBody(this.remove(wrapper));
        ret.setStatus("SUCCESS");
        return ret;
    }
}
