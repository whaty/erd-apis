package com.java2e.martin.erd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java2e.martin.erd.entity.Project;
import com.java2e.martin.erd.mapper.ProjectMapper;
import com.java2e.martin.erd.service.ProjectService;
import com.java2e.martin.erd.util.JsonUtil;
import com.java2e.martin.erd.util.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * PDMan全局配置表 服务实现类
 * </p>
 *
 * @author 狮少
 * @since 2020-10-26
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public Result projectService(String projectId) {
        Project project = this.getById(projectId);
        HashMap<String, Object> map = new HashMap<>(3);
        try {
            map.put("configJSON", JsonUtil.parse(new String(project.getConfigJSON()), Map.class));
            map.put("projectJSON", JsonUtil.parse(new String(project.getProjectJSON()), Map.class));
            map.put("projectName", project.getProjectName());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>().error(e.getMessage());
        }
        return new Result<>(map);
    }
}
