package com.java2e.martin.erd.controller;

import cn.fisok.pdman.command.ExecResult;
import cn.fisok.pdman.command.impl.PingDBCommand;
import com.java2e.martin.erd.command.DBReverseParseCommand;
import com.java2e.martin.erd.command.DbRebaseLineCommand;
import com.java2e.martin.erd.command.DbSqlExecCommand;
import com.java2e.martin.erd.command.DbSyncCommand;
import com.java2e.martin.erd.entity.DbVersion;
import com.java2e.martin.erd.service.DbChangeService;
import com.java2e.martin.erd.service.DbVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;


/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/26
 * @describtion ConnectorController
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("connector")
public class ConnectorController {
    @Autowired
    DbChangeService dbChangeService;

    @Autowired
    private DbVersionService dbVersionService;

    @PostMapping("ping")
    public ExecResult ping(@RequestBody Map map) {
        PingDBCommand pingDBCommand = new PingDBCommand();
        return pingDBCommand.exec(map);
    }

    @PostMapping("dbReverseParse")
    public ExecResult dbReverseParse(@RequestBody Map map) {
        DBReverseParseCommand dbReverseParseCommand = new DBReverseParseCommand();
        return dbReverseParseCommand.exec(map);
    }

    @PostMapping("dbversion")
    public ExecResult dbversion(@RequestBody Map map) {
        ExecResult ret = new ExecResult();
        String version = dbVersionService.dbversion((String) map.get("projectId"));
        if (null == version) {
            DbVersion dbVersion = new DbVersion();
            dbVersion.setProjectId((String) map.get("projectId"));
            dbVersion.setDbVersion("v0.0.0");
            dbVersion.setVersionDesc("基线版本，新建版本时请勿低于该版本");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createTime = LocalDateTime.now().format(formatter);
            dbVersion.setCreatedTime(createTime);
            dbVersionService.save(dbVersion);
            version = "v0.0.0";
        }
        ret.setBody(version);
        ret.setStatus("SUCCESS");
        return ret;

    }


    @PostMapping("rebaseline")
    public ExecResult rebaseline(@RequestBody Map map) {
        ExecResult ret = new ExecResult();
        ret.setBody(dbVersionService.rebaseline((String) map.get("projectId")));
        ret.setStatus("SUCCESS");
        DbRebaseLineCommand dbRebaseLineCommand = new DbRebaseLineCommand();
        return dbRebaseLineCommand.exec(map);
    }

    @PostMapping("dbsync")
    public ExecResult dbsync(@RequestBody Map map) {
        DbSyncCommand dbSyncCommand = new DbSyncCommand();
        return dbSyncCommand.exec(map);
    }


    @PostMapping("sqlexec")
    public ExecResult sqlexec(@RequestBody Map map) {
        DbSqlExecCommand dbSqlExecCommand = new DbSqlExecCommand();
        return dbSqlExecCommand.exec(map);
    }

    @PostMapping("updateVersion")
    public ExecResult updateVersion(@RequestBody Map<String, Object> params) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String version = (String) params.get("version");
        String versionDesc = (String) params.get("versionDesc");
        String projectId = (String) params.get("projectId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = LocalDateTime.now().format(formatter);
        DbVersion dbVersion = new DbVersion();
        dbVersion.setId(id);
        dbVersion.setDbVersion(version);
        dbVersion.setVersionDesc(versionDesc);
        dbVersion.setProjectId(projectId);
        dbVersion.setCreatedTime(createTime);
        ExecResult ret = new ExecResult();
        ret.setBody(dbVersionService.save(dbVersion));
        ret.setStatus("SUCCESS");
        return ret;
    }


}
