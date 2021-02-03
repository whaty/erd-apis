package com.java2e.martin.erd.service;

import cn.fisok.pdman.command.ExecResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java2e.martin.erd.entity.DbChange;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 版本表 服务类
 * </p>
 *
 * @author 狮少
 * @since 2020-10-28
 */
@Transactional(rollbackFor = Exception.class)
public interface DbChangeService extends IService<DbChange> {

    /**
     * 查询历史版本
     *
     * @param projectId
     * @return
     */
    ExecResult loadHistory(String projectId);

    /**
     * 删除版本
     *
     * @param projectId
     * @return
     */
    ExecResult deleteHistory(String changeId);

    /**
     * 删除项目下所有版本版本
     *
     * @param projectId
     * @return
     */
    ExecResult deleteAllHistory(String projectId);

    /**
     * byte[]字段转json
     *
     * @param dbChanges
     * @return
     */
    ExecResult getHashMapsByDbChanges(List<DbChange> dbChanges);
}
