package com.java2e.martin.erd.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.erd.config.MyInvocationSecurityMetadataSourceService;
import com.java2e.martin.erd.entity.SysPermission;
import com.java2e.martin.erd.service.SysPermissionService;
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
import java.util.List;
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
@RequestMapping("sysPermission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private MyInvocationSecurityMetadataSourceService myInvocationSecurityMetadataSourceService;


    /**
     * 添加
     *
     * @param sysPermission SysPermission
     * @return ExecResult
     */
    @PostMapping("/add")
    public Result<Boolean> save(@RequestBody SysPermission sysPermission) {
        Result<Boolean> booleanResult = new Result<>(sysPermissionService.save(sysPermission));
        myInvocationSecurityMetadataSourceService.loadResourceDefine();
        return booleanResult;
    }

    /**
     * 删除
     *
     * @param sysPermission SysPermission
     * @return ExecResult
     */
    @PostMapping("/delete")
    public Result<Boolean> removeById(@RequestBody SysPermission sysPermission) {
        Result<Boolean> booleanResult = new Result<>(sysPermissionService.removeById(sysPermission.getId()));
        myInvocationSecurityMetadataSourceService.loadResourceDefine();
        return booleanResult;
    }

    /**
     * 编辑
     *
     * @param sysPermission SysPermission
     * @return ExecResult
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysPermission sysPermission) {
        Result<Boolean> booleanResult = new Result<>(sysPermissionService.updateById(sysPermission));
        myInvocationSecurityMetadataSourceService.loadResourceDefine();
        return booleanResult;
    }

    /**
     * 通过ID查询
     *
     * @param id String
     * @return SysPermission
     */
    @GetMapping("/get/{id}")
    public SysPermission getById(@PathVariable String id) {
        return sysPermissionService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param params 分页以及查询参数
     * @return Page
     */
    @PostMapping("/page")
    public IPage getPage(@RequestBody Page page) {
        return sysPermissionService.page(page);
    }

    /**
     * 分页查询
     *
     * @param params 分页以及查询参数
     * @return Page
     */
    @PostMapping("/fetchPermission")
    public Map fetchPermission(@RequestBody Map map) {
        HashMap<String, List> result = new HashMap<>(2);
        result.put("allPermissions", sysPermissionService.allPermissions(map));
        result.put("checkedPermissions", sysPermissionService.checkedPermissions(map));
        return result;
    }


}

