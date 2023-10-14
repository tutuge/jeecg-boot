 package org.jeecg.modules.online.config.dUtil;

 import com.baomidou.mybatisplus.annotation.DbType;
 import org.apache.commons.lang.StringUtils;
 import org.jeecg.common.util.dynamic.db.DbTypeUtils;

 public class a {
   private String a;

   private String b;

   private String c;

   private int d;

   private String e;

   private String f;

   private String g;

   private int h;

   private String i;

   private String j;

   private String k;

   private String l;

   public boolean equals(Object obj) {
     if (obj == this)
       return true;
     if (!(obj instanceof a))
       return false;
     a a1 = (a)obj;
     if (this.e.contains("date") || this.e.contains("blob") || this.e.contains("text"))
       return (this.c.equals(a1.getColumnName()) && this.i
         .equals(a1.i) &&
         a(this.f, a1.getComment()) && a(this.g, a1.getFieldDefault()));
     return (this.e.equals(a1.getColunmType()) && this.i
       .equals(a1.i) && this.d == a1.getColumnSize() &&
       a(this.f, a1.getComment()) && a(this.g, a1.getFieldDefault()));
   }

   public boolean a(DbType paramDbType, a parama) {
     String str = parama.getColunmType();
     if (DbTypeUtils.dbTypeIf(paramDbType, new DbType[] { DbType.ORACLE, DbType.ORACLE_12C })) {
       if ("datetime".equalsIgnoreCase(str) && "date".equalsIgnoreCase(this.e))
         return true;
     } else if (DbTypeUtils.dbTypeIsSqlServer(paramDbType)) {
       if ("date".equalsIgnoreCase(str) && "datetime".equalsIgnoreCase(this.e))
         return true;
     }
     return this.e.equalsIgnoreCase(str);
   }

   public boolean a(Object paramObject, DbType paramDbType) {
     if (paramObject == this)
       return true;
     if (!(paramObject instanceof a))
       return false;
     a a1 = (a)paramObject;
     if (!a(paramDbType, a1))
       return false;
     if (DbTypeUtils.dbTypeIsSqlServer(paramDbType)) {
       if (this.e.contains("date") || this.e.contains("blob") || this.e.contains("text"))
         return (this.c.equals(a1.getColumnName()) && this.i.equals(a1.i));
       return (this.e.equals(a1.getColunmType()) && this.i.equals(a1.i) && this.d == a1.getColumnSize() && this.h == a1.getDecimalDigits() && a(this.g, a1.getFieldDefault()));
     }
     if (DbTypeUtils.dbTypeIsPostgre(paramDbType)) {
       if (this.e.contains("date") || this.e.contains("blob") || this.e.contains("text"))
         return (this.c.equals(a1.getColumnName()) && this.i.equals(a1.i));
       return (this.e.equals(a1.getColunmType()) && this.i.equals(a1.i) && this.d == a1.getColumnSize() && this.h == a1.getDecimalDigits() && a(this.g, a1.getFieldDefault()));
     }
     if (DbTypeUtils.dbTypeIsOracle(paramDbType)) {
       if (this.e.contains("date") || this.e.contains("blob") || this.e.contains("text"))
         return (a(paramDbType, a1) && this.c.equals(a1.getColumnName()) && this.i.equals(a1.i));
       return (this.e.equals(a1.getColunmType()) && this.i.equals(a1.i) && this.d == a1.getColumnSize() && this.h == a1.getDecimalDigits() && a(this.g, a1.getFieldDefault()));
     }
     if (this.e.contains("date") || this.e.contains("blob") || this.e.contains("text"))
       return (a(paramDbType, a1) && this.c.equals(a1.getColumnName()) && this.i
         .equals(a1.i) &&
         a(this.f, a1.getComment()) && a(this.g, a1.getFieldDefault()));
     return (this.e.equals(a1.getColunmType()) && this.i
       .equals(a1.i) && this.d == a1.getColumnSize() && this.h == a1.getDecimalDigits() &&
       a(this.f, a1.getComment()) && a(this.g, a1.getFieldDefault()));
   }

   public boolean a(a parama) {
     if (parama == this)
       return true;
     return a(this.f, parama.getComment());
   }

   public boolean b(a parama) {
     if (parama == this)
       return true;
     return a(this.f, parama.getComment());
   }

   private boolean a(String paramString1, String paramString2) {
     boolean bool1 = StringUtils.isNotEmpty(paramString1);
     boolean bool2 = StringUtils.isNotEmpty(paramString2);
     if ("".equals(paramString2)) {
       if (!bool1)
         return true;
       if (paramString1.toLowerCase().toString().indexOf("null") >= 0)
         return true;
       return false;
     }
     if (bool1 != bool2)
       return false;
     if (bool1)
       return paramString1.equals(paramString2);
     return true;
   }

   public String getColumnName() {
     return this.c;
   }

   public int getColumnSize() {
     return this.d;
   }

   public String getColunmType() {
     return this.e;
   }

   public String getComment() {
     return this.f;
   }

   public int getDecimalDigits() {
     return this.h;
   }

   public String getIsNullable() {
     return this.i;
   }

   public String getOldColumnName() {
     return this.k;
   }

   public int hashCode() {
     return this.d + this.e.hashCode() * this.c.hashCode();
   }

   public void setColumnName(String columnName) {
     this.c = columnName;
   }

   public void setColumnSize(int columnSize) {
     this.d = columnSize;
   }

   public void setColunmType(String colunmType) {
     this.e = colunmType;
   }

   public void setComment(String comment) {
     this.f = comment;
   }

   public void setDecimalDigits(int decimalDigits) {
     this.h = decimalDigits;
   }

   public void setIsNullable(String isNullable) {
     this.i = isNullable;
   }

   public void setOldColumnName(String oldColumnName) {
     this.k = oldColumnName;
   }

   public String toString() {
     return this.c + "," + this.e + "," + this.i + "," + this.d;
   }

   public String getColumnId() {
     return this.b;
   }

   public void setColumnId(String columnId) {
     this.b = columnId;
   }

   public String getTableName() {
     return this.a;
   }

   public void setTableName(String tableName) {
     this.a = tableName;
   }

   public String getFieldDefault() {
     return this.g;
   }

   public void setFieldDefault(String fieldDefault) {
     this.g = fieldDefault;
   }

   public String getPkType() {
     return this.j;
   }

   public void setPkType(String pkType) {
     this.j = pkType;
   }

   public String getRealDbType() {
     return this.l;
   }

   public void setRealDbType(String realDbType) {
     this.l = realDbType;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\d\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
