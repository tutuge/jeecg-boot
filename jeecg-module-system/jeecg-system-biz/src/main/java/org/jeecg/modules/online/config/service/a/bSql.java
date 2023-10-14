package org.jeecg.modules.online.config.service.a;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.config.dUtil.a;
import org.jeecg.modules.online.config.service.DbTableHandleI;

public class bSql implements DbTableHandleI {
    public String getAddColumnSql(a columnMeta) {
        return " ADD COLUMN " + a(columnMeta) + "";
    }

    public String getReNameFieldName(a columnMeta) {
        return "RENAME COLUMN " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName() + "";
    }

    public String getUpdateColumnSql(a cgformcolumnMeta, a datacolumnMeta) {
        return " MODIFY " + a(cgformcolumnMeta, datacolumnMeta) + "";
    }

    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if ("varchar2".equalsIgnoreCase(dataType)) {
            str = "string";
        } else if ("varchar".equalsIgnoreCase(dataType)) {
            str = "string";
        } else if ("nvarchar2".equalsIgnoreCase(dataType)) {
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
        } else if ("timestamp".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if ("datetime".equalsIgnoreCase(dataType)) {
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

    private String a(a parama) {
        String str = "(\"" + parama.getColumnName() + "\"";
        if ("string".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " varchar2(" + parama.getColumnSize() + ")";
        } else if ("date".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " date";
        } else if ("datetime".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " datetime";
        } else if ("int".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " INT";
        } else if ("double".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " NUMBER(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ")";
        } else if ("bigdecimal".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " DECIMAL(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ")";
        } else if ("text".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " CLOB ";
        } else if ("blob".equalsIgnoreCase(parama.getColunmType())) {
            str = str + " BLOB ";
        }
        str = str + (StringUtils.isNotEmpty(parama.getFieldDefault()) ? (" DEFAULT " + parama.getFieldDefault()) : " ");
        str = str + ("Y".equals(parama.getIsNullable()) ? " NULL" : " NOT NULL");
        str = str + ")";
        return str;
    }

    private String a(a parama1, a parama2) {
        String str1 = "";
        String str2 = "";
        if (!parama2.getIsNullable().equals(parama1.getIsNullable()))
            str2 = "Y".equals(parama1.getIsNullable()) ? "NULL" : "NOT NULL";
        if ("string".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " varchar2(" + parama1.getColumnSize() + ")" + str2;
        } else if ("date".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " date " + str2;
        } else if ("datetime".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " datetime " + str2;
        } else if ("int".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " INT " + str2;
        } else if ("double".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " NUMBER(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") " + str2;
        } else if ("bigdecimal".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " DECIMAL(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") " + str2;
        } else if ("blob".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " BLOB " + str2;
        } else if ("text".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = parama1.getColumnName() + " CLOB " + str2;
        }
        str1 = str1 + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" DEFAULT " + parama1.getFieldDefault()) : " ");
        str1 = str1 + str2;
        return str1;
    }

    public String getCommentSql(a columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }

    public String getSpecialHandle(a cgformcolumnMeta, a datacolumnMeta) {
        return null;
    }

    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    public String countIndex(String indexName, String tableName) {
        return "select count(*) from user_ind_columns where index_name=upper('" + indexName + "')";
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\service\a\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
