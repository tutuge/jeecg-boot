package org.jeecg.modules.online.config.service.a;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.config.dUtil.aUtil;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecg.modules.online.config.service.DbTableHandleI;

public class fSql implements DbTableHandleI {
    public String getAddColumnSql(aUtil columnMeta) {
        return " ADD COLUMN " + a(columnMeta) + ";";
    }

    public String getReNameFieldName(aUtil columnMeta) {
        return " RENAME  COLUMN  " + columnMeta.getOldColumnName() + " to " + columnMeta.getColumnName() + ";";
    }

    public String getUpdateColumnSql(aUtil cgformcolumnMeta, aUtil datacolumnMeta) throws AException {
        return c(cgformcolumnMeta, datacolumnMeta);
    }

    public String getSpecialHandle(aUtil cgformcolumnMeta, aUtil datacolumnMeta) {
        return "  ALTER  COLUMN   " + d(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String str = "";
        if ("varchar".equalsIgnoreCase(dataType)) {
            str = "string";
        } else if ("double".equalsIgnoreCase(dataType)) {
            str = "double";
        } else if (dataType.contains("int")) {
            str = "int";
        } else if ("Date".equalsIgnoreCase(dataType)) {
            str = "date";
        } else if ("timestamp".equalsIgnoreCase(dataType)) {
            str = "datetime";
        } else if ("bytea".equalsIgnoreCase(dataType)) {
            str = "blob";
        } else if ("text".equalsIgnoreCase(dataType)) {
            str = "text";
        } else if ("decimal".equalsIgnoreCase(dataType)) {
            str = "bigdecimal";
        } else if ("numeric".equalsIgnoreCase(dataType)) {
            str = "bigdecimal";
        }
        return str;
    }

    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName + " ;";
    }

    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private boolean a(String paramString1, String paramString2) {
        paramString1 = (paramString1 == null) ? "" : paramString1.toLowerCase();
        paramString2 = (paramString2 == null) ? "" : paramString2.toLowerCase();
        String str = "int,double,bigdecimal";
        return (str.indexOf(paramString1) >= 0 && str.indexOf(paramString2) >= 0);
    }

    private String a(aUtil parama1, aUtil parama2) {
        String str1 = getDropColumnSql(parama2.getColumnName());
        String str2 = parama1.getTableName();
        String str3 = String.format("alter table %s", new Object[]{str2});
        String str4 = str3 + getAddColumnSql(parama1);
        return str1 + str4;
    }

    private String b(aUtil parama1, aUtil parama2) {
        String str1 = parama1.getIsNullable();
        String str2 = parama2.getIsNullable();
        str1 = (str1 == null) ? "Y" : str1;
        str2 = (str2 == null) ? "Y" : str2;
        if (!str1.equals(str2)) {
            String str3 = parama1.getTableName();
            String str4 = parama1.getColumnName();
            String str5 = "ALTER table %s ALTER COLUMN %s %s not null;";
            if ("Y".equals(str1))
                return String.format(str5, new Object[]{str3, str4, "drop"});
            if ("N".equals(str1))
                return String.format(str5, new Object[]{str3, str4, "set"});
        }
        return "";
    }

    private String c(aUtil parama1, aUtil parama2) throws AException {
        String str1 = "  ALTER  COLUMN   ";
        if ("string".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = str1 + parama1.getColumnName() + "  type character varying(" + parama1.getColumnSize() + ") ";
        } else if ("date".equalsIgnoreCase(parama1.getColunmType())) {
            if (parama2.getColunmType().toLowerCase().indexOf("date") >= 0) {
                str1 = str1 + parama1.getColumnName() + "  type date ";
            } else {
                str1 = a(parama1, parama2);
            }
        } else if ("datetime".equalsIgnoreCase(parama1.getColunmType())) {
            if (parama2.getColunmType().toLowerCase().indexOf("date") >= 0) {
                str1 = str1 + parama1.getColumnName() + "  type timestamp ";
            } else {
                str1 = a(parama1, parama2);
            }
        } else if ("int".equalsIgnoreCase(parama1.getColunmType())) {
            if (a(parama1.getColunmType(), parama2.getColunmType())) {
                str1 = str1 + parama1.getColumnName() + " type int4";
            } else {
                str1 = a(parama1, parama2);
            }
        } else if ("double".equalsIgnoreCase(parama1.getColunmType())) {
            if (a(parama1.getColunmType(), parama2.getColunmType())) {
                str1 = str1 + parama1.getColumnName() + " type  numeric(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") ";
            } else {
                str1 = a(parama1, parama2);
            }
        } else if ("BigDecimal".equalsIgnoreCase(parama1.getColunmType())) {
            if (a(parama1.getColunmType(), parama2.getColunmType())) {
                str1 = str1 + parama1.getColumnName() + " type  decimal(" + parama1.getColumnSize() + "," + parama1.getDecimalDigits() + ") ";
            } else {
                str1 = a(parama1, parama2);
            }
        } else if ("text".equalsIgnoreCase(parama1.getColunmType())) {
            str1 = str1 + parama1.getColumnName() + " text ";
        } else if ("blob".equalsIgnoreCase(parama1.getColunmType())) {
            throw new AException("blob类型不可修改");
        }
        if (!str1.endsWith(";"))
            str1 = str1 + ";";
        String str2 = b(parama1, parama2);
        str1 = str1 + str2;
        return str1;
    }

    private String d(aUtil parama1, aUtil parama2) {
        String str = "";
        if (!parama1.a(parama2))
            if ("string".equalsIgnoreCase(parama1.getColunmType())) {
                str = parama1.getColumnName();
                str = str + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" SET DEFAULT " + parama1.getFieldDefault()) : " DROP DEFAULT");
            } else if ("date".equalsIgnoreCase(parama1.getColunmType()) || "datetime".equalsIgnoreCase(parama1.getColunmType())) {
                str = parama1.getColumnName();
                str = str + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" SET DEFAULT " + parama1.getFieldDefault()) : " DROP DEFAULT");
            } else if ("int".equalsIgnoreCase(parama1.getColunmType())) {
                str = parama1.getColumnName();
                str = str + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" SET DEFAULT " + parama1.getFieldDefault()) : " DROP DEFAULT");
            } else if ("double".equalsIgnoreCase(parama1.getColunmType())) {
                str = parama1.getColumnName();
                str = str + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" SET DEFAULT " + parama1.getFieldDefault()) : " DROP DEFAULT");
            } else if ("bigdecimal".equalsIgnoreCase(parama1.getColunmType())) {
                str = parama1.getColumnName();
                str = str + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" SET DEFAULT " + parama1.getFieldDefault()) : " DROP DEFAULT");
            } else if ("text".equalsIgnoreCase(parama1.getColunmType())) {
                str = parama1.getColumnName();
                str = str + (StringUtils.isNotEmpty(parama1.getFieldDefault()) ? (" SET DEFAULT " + parama1.getFieldDefault()) : " DROP DEFAULT");
            }
        return str;
    }

    private String a(aUtil parama) {
        String str = "";
        if ("string".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " character varying(" + parama.getColumnSize() + ") ";
        } else if ("date".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " date ";
        } else if ("datetime".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " timestamp ";
        } else if ("int".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " int4";
        } else if ("double".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " numeric(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ") ";
        } else if ("bigdecimal".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " decimal(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ") ";
        } else if ("blob".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " bytea ";
        } else if ("text".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " text ";
        }
        str = str + (StringUtils.isNotEmpty(parama.getFieldDefault()) ? (" DEFAULT " + parama.getFieldDefault()) : " ");
        if ("N".equals(parama.getIsNullable()))
            str = str + " NOT NULL ";
        return str;
    }

    private String b(aUtil parama) {
        String str = "";
        if ("string".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " character varying(" + parama.getColumnSize() + ") ";
        } else if ("date".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " date ";
        } else if ("datetime".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " timestamp ";
        } else if ("int".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " int(" + parama.getColumnSize() + ") ";
        } else if ("double".equalsIgnoreCase(parama.getColunmType())) {
            str = parama.getColumnName() + " numeric(" + parama.getColumnSize() + "," + parama.getDecimalDigits() + ") ";
        }
        return str;
    }

    public String getCommentSql(aUtil columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }

    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    public String countIndex(String indexName, String tableName) {
        return "SELECT count(*) FROM pg_indexes WHERE indexname = '" + indexName + "' and tablename = '" + tableName + "'";
    }
}
