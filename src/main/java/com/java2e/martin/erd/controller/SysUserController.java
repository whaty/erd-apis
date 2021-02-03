package com.java2e.martin.erd.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java2e.martin.erd.entity.SysUser;
import com.java2e.martin.erd.service.SysUserService;
import com.java2e.martin.erd.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 添加
     *
     * @param sysUser SysUser
     * @return ExecResult
     */
    @PostMapping("/add")
    public Result<Boolean> save(@RequestBody SysUser sysUser) {
        return new Result<>(sysUserService.save(sysUser));
    }

    /**
     * 删除
     *
     * @param sysUser SysUser
     * @return ExecResult
     */
    @PostMapping("/delete")
    public Result<Boolean> removeById(@RequestBody SysUser sysUser) {
        return new Result<>(sysUserService.removeById(sysUser.getId()));
    }

    /**
     * 编辑
     *
     * @param sysUser SysUser
     * @return ExecResult
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysUser sysUser) {
        return new Result<>(sysUserService.updateById(sysUser));
    }

    /**
     * 通过ID查询
     *
     * @param id String
     * @return SysUser
     */
    @GetMapping("/get/{id}")
    public SysUser getById(@PathVariable String id) {
        return sysUserService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param page 分页以及查询参数
     * @return Page
     */
    @PostMapping("/page")
    public Page getPage(@RequestBody Page page) {
        List records = sysUserService.selectUserRoles(page);
        page.setRecords(records);
        return page;
    }

    /**
     * 分页查询
     *
     * @param map 分页以及查询参数
     * @return Page
     */
    @PostMapping("/bindRole")
    public Boolean bindRole(@RequestBody Map map) {
        return sysUserService.bindRole(map);
    }


}

