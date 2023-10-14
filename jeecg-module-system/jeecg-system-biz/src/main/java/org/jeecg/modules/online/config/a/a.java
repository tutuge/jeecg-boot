 package org.jeecg.modules.online.config.a;
 
 import org.hibernate.dialect.Dialect;
 import org.hibernate.dialect.function.SQLFunction;
 import org.hibernate.dialect.function.SQLFunctionTemplate;
 import org.hibernate.dialect.function.StandardSQLFunction;
 import org.hibernate.dialect.function.VarArgsSQLFunction;
 import org.hibernate.type.StandardBasicTypes;
 import org.hibernate.type.Type;
 
 public final class a extends Dialect {
   public a() {
     registerColumnType(0, "null");
     registerColumnType(-7, "integer");
     registerColumnType(-6, "integer");
     registerColumnType(5, "integer");
     registerColumnType(4, "integer");
     registerColumnType(-5, "integer");
     registerColumnType(16, "integer");
     registerColumnType(7, "real");
     registerColumnType(8, "real");
     registerColumnType(6, "real");
     registerColumnType(2, "numeric");
     registerColumnType(3, "numeric");
     registerColumnType(1, "text");
     registerColumnType(12, "text");
     registerColumnType(2005, "text");
     registerColumnType(-1, "text");
     registerColumnType(2004, "blob");
     registerColumnType(2005, "clob");
     registerColumnType(91, "date");
     registerColumnType(93, "datetime");
     registerFunction("concat", (SQLFunction)new VarArgsSQLFunction((Type)StandardBasicTypes.STRING, "", "||", ""));
     registerFunction("mod", (SQLFunction)new SQLFunctionTemplate((Type)StandardBasicTypes.INTEGER, "?1 % ?2"));
     registerFunction("substr", (SQLFunction)new StandardSQLFunction("substr", (Type)StandardBasicTypes.STRING));
     registerFunction("substring", (SQLFunction)new StandardSQLFunction("substr", (Type)StandardBasicTypes.STRING));
   }
   
   public boolean a() {
     return true;
   }
   
   public boolean b() {
     return false;
   }
   
   public String getIdentityColumnString() {
     return "integer";
   }
   
   public String getIdentitySelectString() {
     return "select last_insert_rowid()";
   }
   
   public boolean supportsLimit() {
     return true;
   }
   
   protected String getLimitString(String query, boolean hasOffset) {
     return (new StringBuffer(query.length() + 20))
       .append(query)
       .append(hasOffset ? " limit ? offset ?" : " limit ?")
       .toString();
   }
   
   public boolean c() {
     return true;
   }
   
   public String getCreateTemporaryTableString() {
     return "create temporary table if not exists";
   }
   
   public boolean d() {
     return false;
   }
   
   public boolean supportsCurrentTimestampSelection() {
     return true;
   }
   
   public boolean isCurrentTimestampSelectStringCallable() {
     return false;
   }
   
   public String getCurrentTimestampSelectString() {
     return "select current_timestamp";
   }
   
   public boolean supportsUnionAll() {
     return true;
   }
   
   public boolean hasAlterTable() {
     return false;
   }
   
   public boolean dropConstraints() {
     return false;
   }
   
   public String getAddColumnString() {
     return "add column";
   }
   
   public String getForUpdateString() {
     return "";
   }
   
   public boolean supportsOuterJoinForUpdate() {
     return false;
   }
   
   public String getDropForeignKeyString() {
     throw new UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect");
   }
   
   public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
     throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
   }
   
   public String getAddPrimaryKeyConstraintString(String constraintName) {
     throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
   }
   
   public boolean supportsIfExistsBeforeTableName() {
     return true;
   }
   
   public boolean supportsCascadeDelete() {
     return false;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\a\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
