package com.java2e.martin.erd.command;

import cn.fisok.pdman.command.ExecResult;
import cn.fisok.pdman.command.impl.AbstractDBCommand;
import cn.fisok.raw.kit.JdbcKit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/29
 * @describtion DbVersionCommand
 * @since 1.0
 */
public class DbVersionCommand extends AbstractDBCommand<ExecResult> {
    @Override
    public ExecResult exec(Map<String, String> params) {
        super.init(params);
        ExecResult ret = new ExecResult();
        Connection conn = null;
        String dbType = null;

        try {
            conn = JdbcKit.getConnection(this.driverClassName, this.url, this.username, this.password);
            Statement statement = conn.createStatement();
            String sql = "SELECT DB_VERSION FROM DB_VERSION WHERE DB_VERSION=(SELECT max(DB_VERSION) FROM DB_VERSION)";
            statement.executeQuery(sql);
            ResultSet resultSet = statement.getResultSet();
            String version = "";
            while (resultSet.next()) {
                version += resultSet.getString(1);
            }
            ret.setBody(version);
            ret.setStatus("SUCCESS");
            return ret;
        } catch (Exception var6) {
            var6.printStackTrace();
            ret.setStatus("FAILED");
            ret.setBody(var6.getMessage());
            this.logger.error("", var6);
            return ret;
        } finally {
            JdbcKit.close(conn);
        }


    }


}
