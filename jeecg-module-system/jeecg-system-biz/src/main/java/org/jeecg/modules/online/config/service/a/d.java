 package org.jeecg.modules.online.config.service.a;

 import org.apache.commons.lang.StringUtils;
 import org.jeecg.modules.online.config.dUtil.a;
 import org.jeecg.modules.online.config.service.DbTableHandleI;

 public class d implements DbTableHandleI {
   public String getAddColumnSql(a columnMeta) {
     return " ADD COLUMN " + a(columnMeta) + ";";
   }

   public String getReNameFieldName(a columnMeta) {
     return "CHANGE COLUMN " + columnMeta.getOldColumnName() + " " + b(columnMeta) + " ;";
   }

   public String getUpdateColumnSql(a cgformcolumnMeta, a datacolumnMeta) {
     return " MODIFY COLUMN " + b(cgformcolumnMeta, datacolumnMeta) + ";";
   }

   public String getMatchClassTypeByDataType(String dataType, int digits) {
     String str = "";
     if ("varchar".equalsIgnoreCase(dataType)) {
       str = "string";
     } else if ("double".equalsIgnoreCase(dataType)) {
       str = "double";
     } else if ("int".equalsIgnoreCase(dataType)) {
       str = "int";
     } else if ("Date".equalsIgnoreCase(dataType)) {
       str = "date";
     } else if ("Datetime".equalsIgnoreCase(dataType)) {
       str = "datetime";
     } else if ("decimal".equalsIgnoreCase(dataType)) {
       str = "bigdecimal";
     } else if ("text".equalsIgnoreCase(dataType)) {
       str = "text";
     } else if ("blob".equalsIgnoreCase(dataType)) {
       str = "blob";
     }
     return str;
   }

   public String dropTableSQL(String tableName) {
     return " DROP TABLE IF EXISTS " + tableName + " ;";
   }

   public String getDropColumnSql(String fieldName) {
     return " DROP COLUMN " + fieldName + ";";
   }

   private String a(a parama1, a parama2) {
     String str1 = "";
     if ("string".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " varchar(" + parama1.getColumnSize() + ") " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("date".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " date " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("datetime".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " datetime " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("int".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " int(" + parama1.getColumnSize() + ") " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("double".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " double(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("bigdecimal".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " decimal(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("text".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " text " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     } else if ("blob".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " blob " + ("Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL");
     }
     str1 = str1 + (StringUtils.isNotEmpty(parama1.getComment()) ? (" COMMENT '" + parama1.getComment() + "'") : " ");
     str1 = str1 + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" DEFAULT " + parama1.getFieldDefault()) : " ");
     String str2 = parama1.getPkType();
     if ("id".equalsIgnoreCase(parama1.getColumnName()) && str2 != null && ("SEQUENCE"
       .equalsIgnoreCase(str2) || "NATIVE".equalsIgnoreCase(str2)))
       str1 = str1 + " AUTO_INCREMENT ";
     return str1;
   }

   private String b(a parama1, a parama2) {
     return a(parama1, parama2);
   }

   private String a(a parama) {
     return a(parama, null);
   }

   private String b(a parama) {
     return a(parama, null);
   }

   public String getCommentSql(a columnMeta) {
     return "";
   }

   public String getSpecialHandle(a cgformcolumnMeta, a datacolumnMeta) {
     return null;
   }

   public String dropIndexs(String indexName, String tableName) {
     return "DROP INDEX " + indexName + " ON " + tableName;
   }

   public String countIndex(String indexName, String tableName) {
     return "select COUNT(*) from information_schema.statistics where table_name = '" + tableName + "'  AND index_name = '" + indexName + "'";
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\service\a\d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
