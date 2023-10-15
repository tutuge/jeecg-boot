package org.jeecg.modules.online.config.dUtil;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.dynamic.db.DbTypeUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.config.bAttribute.bDataBaseConfig;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecg.modules.online.config.service.a.*;
import org.jeecg.modules.online.config.service.a.cSql;
import org.jeecg.modules.online.config.service.a.dCol;
import org.jeecg.modules.online.config.service.a.fSql;
import org.jeecg.modules.online.config.service.a.g;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class eDbTableHandle {
    private static final Logger b = LoggerFactory.getLogger(eDbTableHandle.class);

    public static String a = "";

    public static DbTableHandleI getTableHandle() throws SQLException, AException {
        return a((bDataBaseConfig) null);
    }

    public static DbTableHandleI a(bDataBaseConfig paramb) throws SQLException, AException {
        eSql e1;
        bDataBaseConfig b1;
        g g;
        fSql f;
        AException a;
        dCol d = null;
        DbType dbType = c(paramb);
        String str = DbTypeUtils.getDbTypeString(dbType);
        if (DbType.DM.equals(dbType))
            return  new bSql();
        switch (str) {
            case "MYSQL":
                d = new dCol();
                return (DbTableHandleI) d;
            case "MARIADB":
                d = new dCol();
                return (DbTableHandleI) d;
            case "ORACLE":
                return  new eSql();
            case "DM":
                return  new bSql();
            case "SQLSERVER":
                return  new g();
            case "POSTGRESQL":
                return  new fSql();
            case "DB2":
                return  new aSql();
            case "HSQL":
                return (DbTableHandleI) new cSql();
        }
        return (DbTableHandleI) new dCol();
    }

    public static Connection getConnection() throws SQLException {
        DataSource dataSource = (DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        return dataSource.getConnection();
    }

    public static String getDatabaseType() throws SQLException, AException {
        if (oConvertUtils.isNotEmpty(a))
            return a;
        DataSource dataSource =  SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        return a(dataSource);
    }

    public static boolean a() {
        try {
            return "ORACLE".equals(getDatabaseType());
        } catch (SQLException | AException sQLException) {
            sQLException.printStackTrace();
        }
        return false;
    }

    public static String a(DataSource paramDataSource) throws SQLException, AException {
        if ("".equals(a)) {
            Connection connection = paramDataSource.getConnection();
            try {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                String str = databaseMetaData.getDatabaseProductName().toLowerCase();
                if (str.indexOf("mysql") >= 0) {
                    a = "MYSQL";
                } else if (str.indexOf("oracle") >= 0) {
                    a = "ORACLE";
                } else if (str.indexOf("dm") >= 0) {
                    a = "DM";
                } else if (str.indexOf("sqlserver") >= 0 || str.indexOf("sql server") >= 0) {
                    a = "SQLSERVER";
                } else if (str.indexOf("postgresql") >= 0) {
                    a = "POSTGRESQL";
                } else if (str.indexOf("mariadb") >= 0) {
                    a = "MARIADB";
                } else {
                    b.error("数据库类型:[" + str + "]不识别!");
                }
            } catch (Exception exception) {
                b.error(exception.getMessage(), exception);
            } finally {
                if (connection != null && !connection.isClosed())
                    connection.close();
            }
        }
        return a;
    }

    public static String a(Connection paramConnection) throws SQLException, AException {
        if ("".equals(a)) {
            DatabaseMetaData databaseMetaData = paramConnection.getMetaData();
            String str = databaseMetaData.getDatabaseProductName().toLowerCase();
            if (str.indexOf("mysql") >= 0) {
                a = "MYSQL";
            } else if (str.indexOf("oracle") >= 0) {
                a = "ORACLE";
            } else if (str.indexOf("sqlserver") >= 0 || str.indexOf("sql server") >= 0) {
                a = "SQLSERVER";
            } else if (str.indexOf("postgresql") >= 0) {
                a = "POSTGRESQL";
            } else if (str.indexOf("mariadb") >= 0) {
                a = "MARIADB";
            } else {
                b.error("数据库类型:[" + str + "]不识别!");
            }
        }
        return a;
    }

    public static String a(String paramString1, String paramString2) {
        switch (paramString2) {
            case "ORACLE":
            case "DB2":
                return paramString1.toUpperCase();
            case "POSTGRESQL":
                return paramString1.toLowerCase();
        }
        return paramString1;
    }

    public static Boolean a(String paramString) {
        return a(paramString, (bDataBaseConfig) null);
    }

    public static Boolean a(String paramString, bDataBaseConfig paramb) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String[] arrayOfString = {"TABLE"};
            if (paramb == null) {
                connection = getConnection();
            } else {
                connection = b(paramb);
            }
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            DbType dbType = c(paramb);
            String str1 = DbTypeUtils.getDbTypeString(dbType);
            String str2 = a(paramString, str1);
            String str3 = null;
            if (paramb != null) {
                str3 = paramb.getUsername();
            } else {
                bDataBaseConfig b1 = (bDataBaseConfig) SpringContextUtils.getBean(bDataBaseConfig.class);
                str3 = b1.getUsername();
            }
            if (DbTypeUtils.dbTypeIsOracle(dbType) || DbType.DB2.equals(dbType))
                str3 = (str3 != null) ? str3.toUpperCase() : null;
            if (DbTypeUtils.dbTypeIsSqlServer(dbType)) {
                resultSet = databaseMetaData.getTables(connection.getCatalog(), null, str2, arrayOfString);
            } else if (DbTypeUtils.dbTypeIsPostgre(dbType)) {
                resultSet = databaseMetaData.getTables(connection.getCatalog(), "public", str2, arrayOfString);
            } else if (DbType.HSQL.equals(dbType)) {
                resultSet = databaseMetaData.getTables(connection.getCatalog(), "PUBLIC", str2.toUpperCase(), arrayOfString);
            } else {
                resultSet = databaseMetaData.getTables(connection.getCatalog(), str3, str2, arrayOfString);
            }
            if (resultSet.next())
                return Boolean.valueOf(true);
            return Boolean.valueOf(false);
        } catch (SQLException sQLException) {
            throw new RuntimeException();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException sQLException) {
                b.error(sQLException.getMessage(), sQLException);
            }
        }
    }

    public static Map<String, Object> a(List<Map<String, Object>> paramList) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        for (byte b = 0; b < paramList.size(); b++)
            hashMap.put(((Map) paramList.get(b)).get("column_name").toString(), paramList.get(b));
        return (Map) hashMap;
    }

    public static String getDialect() throws SQLException, AException {
        String str = getDatabaseType();
        return b(str);
    }

    public static String b(String paramString) throws SQLException, AException {
        String str = "org.hibernate.dialect.MySQL5InnoDBDialect";
        switch (paramString) {
            case "SQLSERVER":
                str = "org.hibernate.dialect.SQLServerDialect";
                break;
            case "POSTGRESQL":
                str = "org.hibernate.dialect.PostgreSQLDialect";
                break;
            case "ORACLE":
                str = "org.hibernate.dialect.OracleDialect";
                break;
            case "DM":
                str = "org.hibernate.dialect.DmDialect";
                break;
        }
        return str;
    }

    public static String c(String paramString) {
        return paramString;
    }

    public static Connection b(bDataBaseConfig paramb) throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(paramb.getDriverClassName());
        driverManagerDataSource.setUrl(paramb.getUrl());
        driverManagerDataSource.setUsername(paramb.getUsername());
        driverManagerDataSource.setPassword(paramb.getPassword());
        return driverManagerDataSource.getConnection();
    }

    public static DbType c(bDataBaseConfig paramb) {
        if (paramb == null)
            return CommonUtils.getDatabaseTypeEnum();
        return JdbcUtils.getDbType(paramb.getUrl());
    }
}
