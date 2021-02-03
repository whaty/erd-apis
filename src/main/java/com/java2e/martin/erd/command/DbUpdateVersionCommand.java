package com.java2e.martin.erd.command;

import cn.fisok.pdman.command.ExecResult;
import cn.fisok.pdman.command.impl.AbstractDBCommand;
import cn.fisok.raw.kit.JdbcKit;
import cn.hutool.core.util.ArrayUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/29
 * @describtion DbUpdateVersionCommand
 * @since 1.0
 */
public class DbUpdateVersionCommand extends AbstractDBCommand<ExecResult> {
    @Override
    public ExecResult exec(Map<String, String> params) {
        super.init(params);
        return execSqls(params);
    }

    private ExecResult execSqls(Map<String, String> params) {
        super.init(params);
        Connection conn = JdbcKit.getConnection(this.driverClassName, this.url, this.username, this.password);
        String initVersion = Common.getInsertVersionSql(params);
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add(initVersion);
        return Common.execSqls(params, conn, ArrayUtil.toArray(sqlList.iterator(), String.class));
    }
}
