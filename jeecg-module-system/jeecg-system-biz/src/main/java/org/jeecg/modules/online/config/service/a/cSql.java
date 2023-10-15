package org.jeecg.modules.online.config.service.a;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.config.dUtil.aUtil;
import org.jeecg.modules.online.config.service.DbTableHandleI;

import java.util.List;

public class cSql implements DbTableHandleI {
    public String getAddColumnSql(aUtil columnMeta) {
        String str1 = columnMeta.getColumnName();
        String str2 = a(columnMeta);
        String str3 = " ADD " + str1 + " " + str2;
        if (oConvertUtils.isNotEmpty(columnMeta.getFieldDefault())) {
            str3 = str3 + " DEFAULT " + columnMeta.getFieldDefault();
            if (!"Y".equals(columnMeta.getIsNullable()))
                str3 = str3 + " NOT NULL";
        }
        return str3;
    }

    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str1 = dataType.toLowerCase();
        String str2 = "";
        if ("date".equals(str1) || "time".equals(str1)) {
            str2 = "date";
        } else if ("timestamp".equals(str1)) {
            str2 = "datetime";
        } else if ("numeric".equals(str1)) {
            str2 = "bigdecimal";
        } else if ("double".equals(str1)) {
            str2 = "double";
        } else if ("integer".equals(str1)) {
            str2 = "int";
        } else if ("clob".equals(str1)) {
            str2 = "text";
        } else if ("blob".equals(str1)) {
            str2 = "blob";
        } else {
            str2 = "string";
        }
        return str2;
    }

    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName.toUpperCase() + " ";
    }

    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName.toUpperCase() + "";
    }

    private String a(aUtil parama) {
        String str1 = parama.getColunmType().toLowerCase();
        String str2 = "";
        switch (str1) {
            case "string":
                str2 = String.format("varchar(%s)", new Object[]{Integer.valueOf(parama.getColumnSize())});
                return str2;
            case "date":
                str2 = "DATE";
                return str2;
            case "datetime":
                str2 = "TIMESTAMP";
                return str2;
            case "int":
                str2 = "INTEGER";
                return str2;
            case "double":
                str2 = "double";
                return str2;
            case "bigdecimal":
                str2 = String.format("NUMERIC(%s, %s)", new Object[]{Integer.valueOf(parama.getColumnSize()), Integer.valueOf(parama.getDecimalDigits())});
                return str2;
            case "text":
                str2 = "CLOB";
                return str2;
            case "blob":
                str2 = "BLOB";
                return str2;
        }
        str2 = String.format("varchar(%s)", new Object[]{Integer.valueOf(parama.getColumnSize())});
        return str2;
    }

    public String getReNameFieldName(aUtil columnMeta) {
        String str = a(columnMeta);
        return " change " + columnMeta.getOldColumnName() + " " + columnMeta.getColumnName() + " " + str;
    }

    public String getCommentSql(aUtil columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }

    public String getUpdateColumnSql(aUtil cgformcolumnMeta, aUtil datacolumnMeta) {
        return null;
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

    private boolean a(String paramString) {
        String[] arrayOfString = {"clob", "blob", "text", "date", "double", "int"};
        boolean bool = false;
        for (byte b = 0; b < arrayOfString.length; b++) {
            if (arrayOfString[b].equals(paramString)) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    public void handleUpdateMultiSql(aUtil meta, aUtil config, String tableName, List<String> sqlList) {
        String str1 = config.getColumnName();
        String str2 = meta.getColunmType();
        String str3 = config.getColunmType();
        boolean bool = false;
        if ((!str2.equals(str3) || meta.getColumnSize() != config.getColumnSize() || meta.getDecimalDigits() != config.getDecimalDigits()) && (
                !str2.equals(str3) || !a(str3)))
            bool = true;
        if ("Y".equals(config.getIsNullable()) && !config.getIsNullable().equals(meta.getIsNullable()))
            bool = true;
        if ("N".equals(config.getIsNullable()) && !config.getIsNullable().equals(meta.getIsNullable()))
            bool = true;
        String str4 = meta.getFieldDefault();
        String str5 = config.getFieldDefault();
        if (!oConvertUtils.isEmpty(str4) || !oConvertUtils.isEmpty(str5))
            if (!str5.equals(str4))
                bool = true;
        if (bool) {
            String str6 = String.format("alter table %s", new Object[]{tableName});
            String str7 = str6 + getDropColumnSql(meta.getColumnName());
            sqlList.add(str7);
            String str8 = str6 + getAddColumnSql(config);
            sqlList.add(str8);
        }
        if (!meta.b(config)) {
            String str = String.format("COMMENT ON COLUMN %s.%s IS '%s'", new Object[]{tableName, str1, config.getComment()});
            sqlList.add(str);
        }
    }
}
