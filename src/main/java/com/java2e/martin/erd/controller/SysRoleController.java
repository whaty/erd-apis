package com.java2e.martin.erd.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.erd.entity.SysRole;
import com.java2e.martin.erd.service.SysRoleService;
import com.java2e.martin.erd.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shishao
 * @version 1.0
 * @date 2020-11-10
 * @describtion
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 添加
     *
     * @param sysRole SysRole
     * @return ExecResult
     */
    @PostMapping("/add")
    public Result<Boolean> save(@RequestBody SysRole sysRole) {
        return new Result<>(sysRoleService.save(sysRole));
    }

    /**
     * 删除
     *
     * @param sysRole SysRole
     * @return ExecResult
     */
    @PostMapping("/delete")
    public Result<Boolean> removeById(@RequestBody SysRole sysRole) {
        return new Result<>(sysRoleService.removeById(sysRole.getId()));
    }

    /**
     * 编辑
     *
     * @param sysRole SysRole
     * @return ExecResult
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysRole sysRole) {
        return new Result<>(sysRoleService.updateById(sysRole));
    }

    /**
     * 通过ID查询
     *
     * @param id String
     * @return SysRole
     */
    @GetMapping("/get/{id}")
    public SysRole getById(@PathVariable String id) {
        return sysRoleService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param params 分页以及查询参数
     * @return Page
     */
    @PostMapping("/page")
    public IPage getPage(@RequestBody Page page) {
        return sysRoleService.page(page);
    }

    /**
     * 查询所有角色
     *
     * @param params 分页以及查询参数
     * @return Page
     */
    @PostMapping("/all")
    public Map getAll(@RequestBody Map params) {
        HashMap<String, Object> result = new HashMap<>(2);
        result.put("roles", sysRoleService.list());
        result.put("currentRole", sysRoleService.currentRole());
        return result;
    }

    /**
     * 保存角色的权限
     *
     * @param params 分页以及查询参数
     * @return Page
     */
    @PostMapping("/savePermission")
    public Boolean savePermission(@RequestBody Map params) {
        return sysRoleService.savePermission(params);
    }


}

