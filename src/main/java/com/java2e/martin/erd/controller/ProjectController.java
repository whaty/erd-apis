package com.java2e.martin.erd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.java2e.martin.erd.entity.Project;
import com.java2e.martin.erd.service.ProjectService;
import com.java2e.martin.erd.util.JsonUtil;
import com.java2e.martin.erd.util.Query;
import com.java2e.martin.erd.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;


/**
 * <p>
 * PDMan全局配置表 前端控制器
 * </p>
 *
 * @author 狮少
 * @since 2020-10-26
 */
@Slf4j
@RestController
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 添加
     *
     * @param map Map
     * @return ExecResult
     */
    @PostMapping("/save")
    public Result save(@RequestBody Map map) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        Object projectName = map.get("projectName");
        if (projectName == null) {
            return new Result<>().error("projectName为空");
        }
        wrapper.eq("project_name", projectName);
        Project selectOne = projectService.getOne(wrapper);
        Project project = new Project();

        try {
            project.setProjectName(projectName.toString());
            project.setConfigJSON(JsonUtil.generate(map.get("configJSON")).getBytes());
            project.setProjectJSON(JsonUtil.generate(map.get("projectJSON")).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>().error(e.getMessage());
        }
        if (selectOne == null) {
            return new Result<>(projectService.save(project));
        } else {
            return new Result<>(projectService.update(project, wrapper));
        }
    }

    @GetMapping("/info/{projectId}")
    public Result projectService(@PathVariable String projectId) {
        return projectService.projectService(projectId);
    }


    /**
     * 删除
     *
     * @param project Project
     * @return ExecResult
     */
    @PostMapping("/delete")
    public Result<Boolean> removeById(@RequestBody Project project) {
        return new Result<>(projectService.removeById(project.getId()));
    }

    /**
     * 编辑
     *
     * @param project Project
     * @return ExecResult
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Project project) {
        project.setUpdatedTime(new Date());
        return new Result<>(projectService.updateById(project));
    }

    /**
     * 通过ID查询
     *
     * @param id String
     * @return Project
     */
    @GetMapping("/get/{id}")
    public Project getById(@PathVariable String id) {
        return projectService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param params 分页以及查询参数
     * @return Page
     */
    @PostMapping("/page")
    public IPage getPage(@RequestBody Map params) {
        return projectService.page(new Query<>(params));
    }


}

