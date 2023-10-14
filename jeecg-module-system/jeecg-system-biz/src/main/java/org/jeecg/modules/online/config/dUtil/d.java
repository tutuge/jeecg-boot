 package org.jeecg.modules.online.config.dUtil;

 import com.alibaba.druid.filter.config.ConfigTools;
 import com.baomidou.mybatisplus.annotation.DbType;
 import freemarker.template.TemplateException;
 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.DatabaseMetaData;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import java.sql.SQLSyntaxErrorException;
 import java.util.ArrayList;
 import java.util.EnumSet;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.HibernateException;
 import org.hibernate.Session;
 import org.hibernate.boot.Metadata;
 import org.hibernate.boot.MetadataSources;
 import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
 import org.hibernate.service.ServiceRegistry;
 import org.hibernate.tool.hbm2ddl.SchemaExport;
 import org.hibernate.tool.schema.TargetType;
 import org.jeecg.common.util.dynamic.db.DbTypeUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.config.bAttribute.a;
 import org.jeecg.modules.online.config.bAttribute.b;
 import org.jeecg.modules.online.config.service.DbTableHandleI;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class d {
   private static final Logger a = LoggerFactory.getLogger(d.class);

   private static final String b = "org/jeecg/modules/online/config/engine/tableTemplate.ftl";

   private static DbTableHandleI c;

   private static ServiceRegistry d = null;

   public d(b paramb) throws SQLException, a {
     c = eDbTableHandleI.a(paramb);
   }

   public d() throws SQLException, a {
     c = eDbTableHandleI.a((b)null);
   }

   public static void a(a parama) throws IOException, TemplateException, HibernateException, SQLException, a {
     DbType dbType = eDbTableHandleI.c(parama.getDbConfig());
     if (DbTypeUtils.dbTypeIsOracle(dbType)) {
       ArrayList<OnlCgformField> arrayList = new ArrayList();
       for (OnlCgformField onlCgformField : parama.getColumns()) {
         if ("int".equals(onlCgformField.getDbType())) {
           onlCgformField.setDbType("double");
           onlCgformField.setDbPointLength(Integer.valueOf(0));
         }
         arrayList.add(onlCgformField);
       }
       parama.setColumns(arrayList);
     }
     String str = h.a("org/jeecg/modules/online/config/engine/tableTemplate.ftl", a(parama, dbType));
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     b b = parama.getDbConfig();
     if (d == null) {
       hashMap.put("hibernate.connection.driver_class", b.getDriverClassName());
       hashMap.put("hibernate.connection.url", b.getUrl());
       hashMap.put("hibernate.connection.username", b.getUsername());
       String str1 = b.getPassword();
       if (str1 != null)
         if (b.getDruid() != null && oConvertUtils.isNotEmpty(b.getDruid().getPublicKey())) {
           try {
             String str2 = ConfigTools.decrypt(b.getDruid().getPublicKey(), str1);
             hashMap.put("hibernate.connection.password", str2);
           } catch (Exception exception) {
             exception.printStackTrace();
           }
         } else {
           hashMap.put("hibernate.connection.password", str1);
         }
       hashMap.put("hibernate.show_sql", Boolean.valueOf(true));
       hashMap.put("hibernate.format_sql", Boolean.valueOf(true));
       hashMap.put("hibernate.temp.use_jdbc_metadata_defaults", Boolean.valueOf(false));
       hashMap.put("hibernate.dialect", DbTypeUtils.getDbDialect(dbType));
       hashMap.put("hibernate.hbm2ddl.auto", "create");
       hashMap.put("hibernate.connection.autocommit", Boolean.valueOf(false));
       hashMap.put("hibernate.current_session_context_class", "thread");
       d = (ServiceRegistry)(new StandardServiceRegistryBuilder()).applySettings(hashMap).build();
     }
     MetadataSources metadataSources = new MetadataSources(d);
     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("utf-8"));
     metadataSources.addInputStream(byteArrayInputStream);
     Metadata metadata = metadataSources.buildMetadata();
     SchemaExport schemaExport = new SchemaExport();
     schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
     byteArrayInputStream.close();
     List list = schemaExport.getExceptions();
     for (Exception exception : list) {
       if ("java.sql.SQLSyntaxErrorException".equals(exception.getCause().getClass().getName())) {
         SQLSyntaxErrorException sQLSyntaxErrorException = (SQLSyntaxErrorException)exception.getCause();
         if ("42000".equals(sQLSyntaxErrorException.getSQLState())) {
           if (1064 == sQLSyntaxErrorException.getErrorCode() || 903 == sQLSyntaxErrorException.getErrorCode()) {
             a.error(sQLSyntaxErrorException.getMessage());
             throw new a("请确认表名是否为关键字。");
           }
           continue;
         }
       } else {
         if ("com.microsoft.sqlserver.jdbc.SQLServerException".equals(exception.getCause().getClass().getName())) {
           if (exception.getCause().toString().indexOf("Incorrect syntax near the keyword") != -1) {
             exception.printStackTrace();
             throw new a(exception.getCause().getMessage());
           }
           a.error(exception.getMessage());
           continue;
         }
         if (DbType.DM.equals(dbType) || DbType.DB2.equals(dbType)) {
           String str1 = exception.getMessage();
           if (str1 != null && str1.indexOf("Error executing DDL \"drop table") >= 0) {
             a.error(str1);
             continue;
           }
         }
       }
       throw new a(exception.getMessage());
     }
   }

   public List<String> b(a parama) throws a, SQLException {
     DbType dbType = eDbTableHandleI.c(parama.getDbConfig());
     String str1 = DbTypeUtils.getDbTypeString(dbType);
     String str2 = eDbTableHandleI.a(parama.getTableName(), str1);
     String str3 = "alter table  " + str2 + " ";
     ArrayList<String> arrayList = new ArrayList();
     try {
       Map<String, a> map1 = a((String)null, str2, parama.getDbConfig());
       Map<String, a> map2 = c(parama);
       Map<String, String> map = a(parama.getColumns());
       for (String str : map2.keySet()) {
         if ("id".equalsIgnoreCase(str))
           continue;
         if (!map1.containsKey(str)) {
           a a3 = map2.get(str);
           String str4 = map.get(str);
           if (map.containsKey(str) && map1.containsKey(str4)) {
             a a4 = map1.get(str4);
             if (DbType.HSQL.equals(dbType)) {
               a(a4, a3, str2, arrayList);
             } else {
               String str6 = c.getReNameFieldName(a3);
               if (DbTypeUtils.dbTypeIsSqlServer(dbType)) {
                 arrayList.add(str6);
               } else {
                 arrayList.add(str3 + str6);
               }
               if (DbType.DB2.equals(dbType)) {
                 a(a4, a3, str2, arrayList);
               } else {
                 if (!a4.equals(a3)) {
                   arrayList.add(str3 + a(a3, a4));
                   if (DbTypeUtils.dbTypeIsPostgre(dbType))
                     arrayList.add(str3 + b(a3, a4));
                 }
                 if (!DbTypeUtils.dbTypeIsSqlServer(dbType) && !a4.b(a3))
                   arrayList.add(c(a3));
               }
             }
             String str5 = c(str, a3.getColumnId());
             arrayList.add(str5);
             continue;
           }
           arrayList.add(str3 + b(a3));
           if (!DbTypeUtils.dbTypeIsSqlServer(dbType) && StringUtils.isNotEmpty(a3.getComment()))
             arrayList.add(c(a3));
           continue;
         }
         a a1 = map1.get(str);
         a a2 = map2.get(str);
         if (DbType.DB2.equals(dbType) || DbType.HSQL.equals(dbType)) {
           a(a1, a2, str2, arrayList);
           continue;
         }
         if (!a1.a(a2, dbType))
           arrayList.add(str3 + a(a2, a1));
         if (!DbTypeUtils.dbTypeIsSqlServer(dbType) && !DbTypeUtils.dbTypeIsOracle(dbType) && !a1.b(a2))
           arrayList.add(c(a2));
       }
       for (String str : map1.keySet()) {
         if (!map2.containsKey(str.toLowerCase()) && !map.containsValue(str.toLowerCase()))
           arrayList.add(str3 + b(str));
       }
       if (DbType.DB2.equals(dbType))
         arrayList.add("CALL SYSPROC.ADMIN_CMD('reorg table " + str2 + "')");
     } catch (SQLException sQLException) {
       throw new RuntimeException();
     }
     return arrayList;
   }

   private static Map<String, Object> a(a parama, DbType paramDbType) {
     String str = DbTypeUtils.getDbTypeString(paramDbType);
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     for (OnlCgformField onlCgformField : parama.getColumns())
       onlCgformField.setDbDefaultVal(c(onlCgformField.getDbDefaultVal()));
     hashMap.put("entity", parama);
     hashMap.put("dataType", str);
     hashMap.put("db", paramDbType.getDb());
     return (Map)hashMap;
   }

   private Map<String, a> a(String paramString1, String paramString2, b paramb) throws SQLException {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     Connection connection = null;
     try {
       connection = eDbTableHandleI.b(paramb);
     } catch (Exception exception) {
       a.error(exception.getMessage(), exception);
     }
     DatabaseMetaData databaseMetaData = connection.getMetaData();
     String str = paramb.getUsername();
     DbType dbType = eDbTableHandleI.c(paramb);
     if (DbTypeUtils.dbTypeIsOracle(dbType) || DbType.DB2.equals(dbType))
       str = str.toUpperCase();
     ResultSet resultSet = null;
     if (DbTypeUtils.dbTypeIsSqlServer(dbType)) {
       resultSet = databaseMetaData.getColumns(connection.getCatalog(), null, paramString2, "%");
     } else if (DbTypeUtils.dbTypeIsPostgre(dbType)) {
       resultSet = databaseMetaData.getColumns(connection.getCatalog(), "public", paramString2, "%");
     } else if (DbType.HSQL.equals(dbType)) {
       resultSet = databaseMetaData.getColumns(connection.getCatalog(), "PUBLIC", paramString2.toUpperCase(), "%");
     } else {
       resultSet = databaseMetaData.getColumns(connection.getCatalog(), str, paramString2, "%");
     }
     while (resultSet.next()) {
       a a = new a();
       a.setTableName(paramString2);
       String str1 = resultSet.getString("COLUMN_NAME").toLowerCase();
       a.setColumnName(str1);
       String str2 = resultSet.getString("TYPE_NAME");
       int i = resultSet.getInt("DECIMAL_DIGITS");
       String str3 = c.getMatchClassTypeByDataType(str2, i);
       a.setColunmType(str3);
       a.setRealDbType(str2);
       int j = resultSet.getInt("COLUMN_SIZE");
       a.setColumnSize(j);
       a.setDecimalDigits(i);
       String str4 = (resultSet.getInt("NULLABLE") == 1) ? "Y" : "N";
       a.setIsNullable(str4);
       String str5 = resultSet.getString("REMARKS");
       a.setComment(str5);
       String str6 = resultSet.getString("COLUMN_DEF");
       String str7 = (c(str6) == null) ? "" : c(str6);
       a.setFieldDefault(str7);
       hashMap.put(str1, a);
     }
     return (Map)hashMap;
   }

   private Map<String, a> c(a parama) {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     List list = parama.getColumns();
     for (OnlCgformField onlCgformField : list) {
       a a1 = new a();
       a1.setTableName(parama.getTableName().toLowerCase());
       a1.setColumnId(onlCgformField.getId());
       a1.setColumnName(onlCgformField.getDbFieldName().toLowerCase());
       a1.setColumnSize(onlCgformField.getDbLength().intValue());
       a1.setColunmType(onlCgformField.getDbType().toLowerCase());
       a1.setIsNullable((onlCgformField.getDbIsNull().intValue() == 1) ? "Y" : "N");
       a1.setComment(onlCgformField.getDbFieldTxt());
       a1.setDecimalDigits(onlCgformField.getDbPointLength().intValue());
       a1.setFieldDefault(c(onlCgformField.getDbDefaultVal()));
       a1.setPkType((parama.getJformPkType() == null) ? "UUID" : parama.getJformPkType());
       a1.setOldColumnName((onlCgformField.getDbFieldNameOld() != null) ? onlCgformField.getDbFieldNameOld().toLowerCase() : null);
       hashMap.put(onlCgformField.getDbFieldName().toLowerCase(), a1);
     }
     return (Map)hashMap;
   }

   private Map<String, String> a(List<OnlCgformField> paramList) {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     for (OnlCgformField onlCgformField : paramList)
       hashMap.put(onlCgformField.getDbFieldName(), onlCgformField.getDbFieldNameOld());
     return (Map)hashMap;
   }

   private String b(String paramString) {
     return c.getDropColumnSql(paramString);
   }

   private String a(a parama1, a parama2) throws a {
     return c.getUpdateColumnSql(parama1, parama2);
   }

   private String b(a parama1, a parama2) {
     return c.getSpecialHandle(parama1, parama2);
   }

   private void a(a parama1, a parama2, String paramString, List<String> paramList) {
     c.handleUpdateMultiSql(parama1, parama2, paramString, paramList);
   }

   private String a(a parama) {
     return c.getReNameFieldName(parama);
   }

   private String b(a parama) {
     return c.getAddColumnSql(parama);
   }

   private String c(a parama) {
     return c.getCommentSql(parama);
   }

   private String c(String paramString1, String paramString2) {
     return "update onl_cgform_field set DB_FIELD_NAME_OLD = '" + paramString1 + "' where ID ='" + paramString2 + "'";
   }

   private int a(String paramString1, String paramString2, Session paramSession) {
     return paramSession.createSQLQuery("update onl_cgform_field set DB_FIELD_NAME_OLD= '" + paramString1 + "' where ID ='" + paramString2 + "'").executeUpdate();
   }

   private static String c(String paramString) {
     if (StringUtils.isNotEmpty(paramString))
       try {
         Double.valueOf(paramString);
       } catch (Exception exception) {
         if (!paramString.startsWith("'") || !paramString.endsWith("'"))
           paramString = "'" + paramString + "'";
       }
     return paramString;
   }

   public String a(String paramString1, String paramString2) {
     return c.dropIndexs(paramString1, paramString2);
   }

   public String b(String paramString1, String paramString2) {
     return c.countIndex(paramString1, paramString2);
   }

   public static List<String> a(String paramString) throws SQLException {
     Connection connection = null;
     ResultSet resultSet = null;
     ArrayList<String> arrayList = new ArrayList();
     try {
       connection = eDbTableHandleI.getConnection();
       DatabaseMetaData databaseMetaData = connection.getMetaData();
       resultSet = databaseMetaData.getIndexInfo(null, null, paramString, false, false);
       ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
       while (resultSet.next()) {
         String str = resultSet.getString("INDEX_NAME");
         if (oConvertUtils.isEmpty(str))
           str = resultSet.getString("index_name");
         if (oConvertUtils.isNotEmpty(str))
           arrayList.add(str);
       }
     } catch (SQLException sQLException) {
       a.error(sQLException.getMessage(), sQLException);
     } finally {
       if (connection != null)
         connection.close();
     }
     return arrayList;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\d\d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
