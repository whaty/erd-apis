package com.java2e.martin.erd.command;

import cn.fisok.pdman.command.ExecResult;
import cn.fisok.pdman.command.impl.AbstractDBCommand;
import cn.fisok.pdman.dbreverse.DataType;
import cn.fisok.pdman.dbreverse.Entity;
import cn.fisok.pdman.dbreverse.Field;
import cn.fisok.pdman.dbreverse.Module;
import cn.fisok.pdman.dbreverse.ParseDataModel;
import cn.fisok.raw.kit.JdbcKit;
import cn.fisok.raw.kit.StringKit;
import cn.fisok.raw.kit.UpOrLow;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/11/2
 * @describtion DBReverseParseCommand
 * @since 1.0
 */
public class DBReverseParseCommand extends AbstractDBCommand<ExecResult> {
    public DBReverseParseCommand() {
    }

    @Override
    public ExecResult exec(Map<String, String> params) {
        super.init(params);
        ExecResult ret = new ExecResult();
        Connection conn = null;
        String dbType = null;

        try {
            conn = JdbcKit.getConnection(this.driverClassName, this.url, this.username, this.password);
            dbType = StringKit.nvl(conn.getMetaData().getDatabaseProductName(), "MYSQL").toUpperCase();
        } catch (Exception var6) {
            ret.setStatus("FAILED");
            ret.setBody(var6.getMessage());
            this.logger.error("", var6);
            return ret;
        }

        ParseDataModel dataModel = new ParseDataModel();
        dataModel.setDbType(dbType);
        this.parseFill(dataModel, dbType, conn, (String) params.get("flag"));
        ret.setBody(dataModel);
        ret.setStatus("SUCCESS");
        return ret;
    }

    protected void parseFill(ParseDataModel dataModel, String dbType, Connection conn, String flag) {
        List<Entity> entities = this.getTableEntityList(dbType, conn, dataModel, flag);
        Module module = new Module();
        module.setCode("DB_REVERSE_" + dbType.toUpperCase());
        module.setName("逆向解析_" + dbType.toUpperCase());
        module.setEntities(entities);
        dataModel.setModule(module);
    }

    protected List<Entity> getTableEntityList(String dbType, Connection conn, ParseDataModel dataModel, String flag) {
        List<Entity> tableEntities = new ArrayList();
        DatabaseMetaData dbmd = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            dbmd = conn.getMetaData();
            String schemaPattern = null;
            if ("ORACLE".equalsIgnoreCase(dbType) || "DB2".equalsIgnoreCase(dbType) || "DM DBMS".equalsIgnoreCase(dbType)) {
                schemaPattern = conn.getMetaData().getUserName().toUpperCase();
            }

            rs = dbmd.getTables(conn.getCatalog(), schemaPattern, "%", new String[]{"TABLE"});
            stmt = rs.getStatement();

            while (rs.next()) {
                if (!rs.getString("TABLE_NAME").equalsIgnoreCase("PDMAN_DB_VERSION") && !rs.getString("TABLE_NAME").equalsIgnoreCase("trace_xe_action_map") && !rs.getString("TABLE_NAME").equalsIgnoreCase("trace_xe_event_map")) {
                    String originTableName = rs.getString("TABLE_NAME");
                    String tableName = this.adjustUpOrLow(rs.getString("TABLE_NAME"), flag);
                    String remarks = rs.getString("REMARKS");
                    Entity entity = new Entity();
                    entity.setTitle(tableName);
                    entity.setChnname(remarks);
                    this.parseFillTable(dbType, entity, conn, dataModel, flag, originTableName);
                    tableEntities.add(entity);
                }
            }
        } catch (Exception var17) {
            this.logger.error("读取表清单出错", var17);
            throw new RuntimeException(var17);
        } finally {
            JdbcKit.close(stmt);
            JdbcKit.close(rs);
        }

        return tableEntities;
    }

    private static String getSchema(String dbType, Connection conn) throws SQLException {
        String schema = null;
        if ("ORACLE".equalsIgnoreCase(dbType) || "DB2".equalsIgnoreCase(dbType)) {
            schema = conn.getMetaData().getUserName().toUpperCase();
            if (schema == null || schema.length() == 0) {
                throw new RuntimeException(dbType + "数据库,schema不允许为空");
            }
        }

        return schema;
    }

    protected void parseFillTable(String dbType, Entity entity, Connection conn, ParseDataModel dataModel, String flag, String originTableName) {
        ResultSet rs = null;
        Statement stmt = null;
        ResultSet pkRs = null;
        Statement pkStmt = null;

        try {
            DatabaseMetaData connMetaData = conn.getMetaData();
            if (!"MICROSOFT SQL SERVER".equals(dbType) && !dbType.startsWith("MICROSOFT")) {
                rs = connMetaData.getColumns(conn.getCatalog(), getSchema(dbType, conn), originTableName, "%");
                pkRs = connMetaData.getPrimaryKeys(conn.getCatalog(), getSchema(dbType, conn), originTableName);
            } else {
                rs = connMetaData.getColumns((String) null, (String) null, originTableName, (String) null);
                pkRs = connMetaData.getPrimaryKeys((String) null, (String) null, originTableName);
            }

            stmt = rs.getStatement();
            pkStmt = pkRs.getStatement();
            HashSet pkSet = new HashSet();

            String colName;
            while (pkRs.next()) {
                colName = pkRs.getString("COLUMN_NAME");
                pkSet.add(this.adjustUpOrLow(colName, flag));
            }

            while (rs.next()) {
                colName = this.adjustUpOrLow(rs.getString("COLUMN_NAME"), flag);
                String remarks = rs.getString("REMARKS");
                String typeName = rs.getString("TYPE_NAME");
                int dataType = rs.getInt("DATA_TYPE");
                String isAutoincrement = null;
                if (dbType.equals("MYSQL")) {
                    isAutoincrement = rs.getString("IS_AUTOINCREMENT");
                }

                int columnSize = rs.getInt("COLUMN_SIZE");
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");
                String isNullable = rs.getString("IS_NULLABLE");
                Field field = new Field();
                field.setChnname(remarks);
                field.setName(colName);
                field.setType(typeName + "(" + columnSize + "," + decimalDigits + ")");
                field.setPk(pkSet.contains(colName));
                if (dbType.equals("MYSQL")) {
                    field.setAutoIncrement(!"NO".equalsIgnoreCase(isAutoincrement));
                }

                field.setNotNull(!"YES".equalsIgnoreCase(isNullable));
                DataType domainDataType = this.touchDataType(typeName, dataType, columnSize, decimalDigits, dataModel);
                field.setType(domainDataType.getCode());
                entity.getFields().add(field);
            }
        } catch (SQLException var26) {
            this.logger.error("读取数据表" + originTableName + "的字段明细出错", var26);
            throw new RuntimeException("读取数据表" + originTableName + "的字段明细出错", var26);
        } finally {
            JdbcKit.close(stmt);
            JdbcKit.close(rs);
            JdbcKit.close(pkStmt);
            JdbcKit.close(pkRs);
        }

    }

    private DataType touchDataType(String typeName, int dataType, int columnSize, int decimalDigits, ParseDataModel dataModel) {
        List<String> atomList = new ArrayList();
        List<String> lenList = new ArrayList();
        atomList.add(typeName);
        String domainTypeName = typeName;
        if (JdbcKit.isNumeric(dataType)) {
            atomList.add("" + columnSize);
            lenList.add("" + columnSize);
            if (decimalDigits > 0) {
                atomList.add("" + decimalDigits);
                lenList.add("" + decimalDigits);
            }

            domainTypeName = typeName + "(" + StringKit.join(lenList, ",") + ")";
        } else if (JdbcKit.isShortString(dataType)) {
            atomList.add("" + columnSize);
            lenList.add("" + columnSize);
            domainTypeName = typeName + "(" + StringKit.join(lenList, ",") + ")";
        }

        String domainName = StringKit.join(atomList, "_");
        Map<String, DataType> dataTypeMap = dataModel.getDataTypeMap();
        DataType domainDataType = (DataType) dataTypeMap.get(domainName);
        if (domainDataType == null) {
            domainDataType = new DataType();
            domainDataType.setName(domainName);
            domainDataType.setCode(domainName);
            domainDataType.setType(domainTypeName);
            dataTypeMap.put(domainName, domainDataType);
        }

        return domainDataType;
    }

    private String adjustUpOrLow(String tableName, String flag) {
        flag = StringKit.nvl(flag, "");
        if (flag.equals(UpOrLow.DEFAULT.toString())) {
            return tableName;
        } else {
            return flag.equals(UpOrLow.LOWCASE.toString()) ? tableName.toLowerCase() : tableName.toUpperCase();
        }
    }
}
