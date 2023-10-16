package org.jeecg.modules.online.cgform.model;

import org.jeecg.common.util.oConvertUtils;

public class hSort {
    public static final String a = "asc";

    public static final String b = "desc";

    public static final String c = " ORDER BY ";

    public static final String d = "ID";

    private String e;

    private String f;

    private String g;

    public static hSort a() {
        return a("");
    }

    public static hSort a(String paramString) {
        hSort h1 = new hSort("ID");
        h1.setAlias(paramString);
        return h1;
    }

    public String getRealSql() {
        String str = this.g + oConvertUtils.camelToUnderline(this.e);
        if ("asc".equals(this.f)) {
            str = str + " asc";
        } else {
            str = str + " desc";
        }
        return str;
    }

    public hSort() {
    }

    public hSort(String paramString1, String paramString2) {
        this.e = paramString1;
        this.f = paramString2;
        this.g = "";
    }

    public hSort(String paramString) {
        this.f = "desc";
        this.e = paramString;
        this.g = "";
    }

    public String getColumn() {
        return this.e;
    }

    public void setColumn(String column) {
        this.e = column;
    }

    public String getRule() {
        return this.f;
    }

    public void setRule(String rule) {
        this.f = rule;
    }

    public String getAlias() {
        return this.g;
    }

    public void setAlias(String alias) {
        this.g = alias;
    }
}

