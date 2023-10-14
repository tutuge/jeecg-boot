 package org.jeecg.modules.online.config.service.a;

 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.config.dUtil.a;
 import org.jeecg.modules.online.config.service.DbTableHandleI;

 public class g implements DbTableHandleI {
   public String getAddColumnSql(a columnMeta) {
     return " ADD  " + a(columnMeta) + ";";
   }

   public String getReNameFieldName(a columnMeta) {
     return "  sp_rename '" + columnMeta.getTableName() + "." + columnMeta.getOldColumnName() + "', '" + columnMeta.getColumnName() + "', 'COLUMN';";
   }

   public String getUpdateColumnSql(a cgformcolumnMeta, a datacolumnMeta) {
     return " ALTER COLUMN  " + a(cgformcolumnMeta, datacolumnMeta) + ";";
   }

   public String getMatchClassTypeByDataType(String dataType, int digits) {
     String str = "";
     if ("varchar".equalsIgnoreCase(dataType) || "nvarchar".equalsIgnoreCase(dataType)) {
       str = "string";
     } else if ("float".equalsIgnoreCase(dataType)) {
       str = "double";
     } else if ("int".equalsIgnoreCase(dataType)) {
       str = "int";
     } else if ("Datetime".equalsIgnoreCase(dataType)) {
       str = "datetime";
     } else if ("numeric".equalsIgnoreCase(dataType)) {
       str = "bigdecimal";
     } else if ("varbinary".equalsIgnoreCase(dataType) || "image".equalsIgnoreCase(dataType)) {
       str = "blob";
     } else if ("text".equalsIgnoreCase(dataType) || "ntext".equalsIgnoreCase(dataType)) {
       str = "text";
     }
     return str;
   }

   public String dropTableSQL(String tableName) {
     return " DROP TABLE " + tableName + " ;";
   }

   public String getDropColumnSql(String fieldName) {
     return " DROP COLUMN " + fieldName + ";";
   }

   private String a(a parama1, a parama2) {
     String str = "";
     if ("string".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " nvarchar(" + parama1.getColumnSize() + ") " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("date".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " datetime " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("datetime".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " datetime " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("int".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " int " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("double".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " float " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("bigdecimal".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " numeric(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("text".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " ntext " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("blob".equalsIgnoreCase(parama1.getColunmType())) {
       str = parama1.getColumnName() + " image";
     }
     return str;
   }

   private String a(a parama) {
     String str = "";
     if ("string".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " nvarchar(" + parama.getColumnSize() + ") " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("date".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " datetime " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("datetime".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " datetime " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("int".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " int " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("double".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " float " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("bigdecimal".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " numeric(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ") " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("text".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " ntext " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("blob".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " image";
     }
     return str;
   }

   private String b(a parama) {
     String str = "";
     if ("string".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " nvarchar(" + parama.getColumnSize() + ") " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("date".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " datetime " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("datetime".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " datetime " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("int".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " int " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("double".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " float " + ("Y".equals(parama.getIsNullable()) ? "NULL" : "NOT NULL");
     }
     return str;
   }

   public String getCommentSql(a columnMeta) {
     StringBuffer stringBuffer = new StringBuffer("EXECUTE ");
     if (oConvertUtils.isEmpty(columnMeta.getOldColumnName())) {
       stringBuffer.append("sp_addextendedproperty");
     } else {
       stringBuffer.append("sp_updateextendedproperty");
     }
     stringBuffer.append(" N'MS_Description', '");
     stringBuffer.append(columnMeta.getComment());
     stringBuffer.append("', N'SCHEMA', N'dbo', N'TABLE', N'");
     stringBuffer.append(columnMeta.getTableName());
     stringBuffer.append("', N'COLUMN', N'");
     stringBuffer.append(columnMeta.getColumnName() + "'");
     return stringBuffer.toString();
   }

   public String getSpecialHandle(a cgformcolumnMeta, a datacolumnMeta) {
     return null;
   }

   public String dropIndexs(String indexName, String tableName) {
     return "DROP INDEX " + indexName + " ON " + tableName;
   }

   public String countIndex(String indexName, String tableName) {
     return "SELECT count(*) FROM sys.indexes WHERE object_id=OBJECT_ID('" + tableName + "') and NAME= '" + indexName + "'";
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\service\a\g.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
