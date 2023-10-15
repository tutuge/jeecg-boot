 package org.jeecg.modules.online.config.service.a;

 import org.apache.commons.lang.StringUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.config.dUtil.aUtil;
 import org.jeecg.modules.online.config.service.DbTableHandleI;

 public class eSql implements DbTableHandleI {
   public String getAddColumnSql(aUtil columnMeta) {
     return " ADD  " + a(columnMeta) + "";
   }

   public String getReNameFieldName(aUtil columnMeta) {
     return "RENAME COLUMN  " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName() + "";
   }

   public String getUpdateColumnSql(aUtil cgformcolumnMeta, aUtil datacolumnMeta) {
     return " MODIFY   " + a(cgformcolumnMeta, datacolumnMeta) + "";
   }

   public String getMatchClassTypeByDataType(String dataType, int digits) {
     String str = "";
     if ("varchar2".equalsIgnoreCase(dataType))
       str = "string";
     if ("nvarchar2".equalsIgnoreCase(dataType)) {
       str = "string";
     } else if ("double".equalsIgnoreCase(dataType)) {
       str = "double";
     } else if ("number".equalsIgnoreCase(dataType) && digits == 0) {
       str = "int";
     } else if ("number".equalsIgnoreCase(dataType) && digits != 0) {
       str = "double";
     } else if ("int".equalsIgnoreCase(dataType)) {
       str = "int";
     } else if ("Date".equalsIgnoreCase(dataType)) {
       str = "date";
     } else if ("TIMESTAMP".equalsIgnoreCase(dataType)) {
       str = "datetime";
     } else if ("blob".equalsIgnoreCase(dataType)) {
       str = "blob";
     } else if ("clob".equalsIgnoreCase(dataType)) {
       str = "text";
     }
     return str;
   }

   public String dropTableSQL(String tableName) {
     return " DROP TABLE  " + tableName.toLowerCase() + " ";
   }

   public String getDropColumnSql(String fieldName) {
     return " DROP COLUMN " + fieldName.toUpperCase() + "";
   }

   private String a(aUtil parama) {
     String str = "";
     if ("string".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " varchar2(" + parama.getColumnSize() + ")";
     } else if ("date".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " date";
     } else if ("datetime".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " date";
     } else if ("int".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " NUMBER(" + parama.getColumnSize() + ")";
     } else if ("double".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " NUMBER(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ")";
     } else if ("bigdecimal".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " NUMBER(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ")";
     } else if ("text".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " CLOB ";
     } else if ("blob".equalsIgnoreCase(parama.getColunmType())) {
       str = parama.getColumnName() + " BLOB ";
     }
     str = str + (StringUtils.isNotEmpty(parama.getFieldDefault()) ? (" DEFAULT " + parama.getFieldDefault()) : " ");
     str = str + ("Y".equals(parama.getIsNullable()) ? " NULL" : " NOT NULL");
     return str;
   }

   private String a(aUtil parama1, aUtil parama2) {
     String str1 = "";
     String str2 = "";
     String str3 = parama2.getRealDbType();
     if (!parama2.getIsNullable().equals(parama1.getIsNullable()))
       str2 = "Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL";
     if ("string".equalsIgnoreCase(parama1.getColunmType())) {
       if (oConvertUtils.isEmpty(str3) || str3.toLowerCase().indexOf("varchar") < 0)
         str3 = "varchar2";
       str1 = parama1.getColumnName() + " " + str3 + "(" + parama1.getColumnSize() + ") ";
     } else if ("date".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " date ";
     } else if ("datetime".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " date ";
     } else if ("int".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " NUMBER(" + parama1.getColumnSize() + ") ";
     } else if ("double".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " NUMBER(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") ";
     } else if ("bigdecimal".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " NUMBER(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") ";
     } else if ("blob".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " BLOB ";
     } else if ("text".equalsIgnoreCase(parama1.getColunmType())) {
       str1 = parama1.getColumnName() + " CLOB ";
     }
     str1 = str1 + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" DEFAULT " + parama1.getFieldDefault()) : " ");
     str1 = str1 + str2;
     return str1;
   }

   public String getCommentSql(aUtil columnMeta) {
     return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
   }

   public String getSpecialHandle(aUtil cgformcolumnMeta, aUtil datacolumnMeta) {
     return null;
   }

   public String dropIndexs(String indexName, String tableName) {
     return "DROP INDEX " + indexName;
   }

   public String countIndex(String indexName, String tableName) {
     return "select count(*) from user_ind_columns where index_name=upper('" + indexName + "')";
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\service\a\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
