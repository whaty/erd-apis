package com.java2e.martin.erd.command;

import cn.fisok.pdman.command.ExecResult;
import cn.fisok.raw.kit.JdbcKit;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/11/2
 * @describtion Common
 * @since 1.0
 */
@Slf4j
public class Common {
    public static String getInsertVersionSql(Map<String, String> params) {
        String initVersion = "insert into db_version(id,db_version,version_desc,created_time,project_id) values ('%s','%s','%s','%s','%s');";
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String version = params.get("version");
        String versionDesc = params.get("versionDesc");
        String projectId = params.get("projectId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = LocalDateTime.now().format(formatter);
        initVersion = String.format(initVersion, id, version, versionDesc, createTime, projectId);
        log.info("=========updateVersion===========", initVersion);
        return initVersion;
    }

    public static ExecResult execSqls(Map<String, String> params, Connection conn, String[] sqls) {
        ExecResult ret = new ExecResult();
        try {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            for (String s : sqls) {
                if (StrUtil.isNotBlank(s)) {
                    log.info("=========sql===========", s);
                    statement.addBatch(s);
                }
            }
            statement.executeBatch();
            ret.setBody(params.get("sql"));
            ret.setStatus("SUCCESS");
            conn.setAutoCommit(true);
            return ret;
        } catch (Exception var6) {
            var6.printStackTrace();
            ret.setBody(var6.getMessage());
            log.error(var6.getMessage(), var6);
            // 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
            try {
                if (!conn.isClosed()) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ret.setBody(e.getMessage());
                log.error(e.getMessage(), var6);
            } finally {
                ret.setStatus("FAILED");
                return ret;
            }
        } finally {
            JdbcKit.close(conn);
        }
    }
}
