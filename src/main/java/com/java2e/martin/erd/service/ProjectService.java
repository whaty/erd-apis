package com.java2e.martin.erd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java2e.martin.erd.entity.Project;
import com.java2e.martin.erd.util.Result;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * PDMan全局配置表 服务类
 * </p>
 *
 * @author 狮少
 * @since 2020-10-26
 */
@Transactional(rollbackFor = Exception.class)
public interface ProjectService extends IService<Project> {

    /**
     * 根据id查询project信息
     *
     * @param projectId
     * @return
     */
    Result projectService(String projectId);
}
