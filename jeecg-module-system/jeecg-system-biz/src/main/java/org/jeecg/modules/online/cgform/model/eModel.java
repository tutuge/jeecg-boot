package org.jeecg.modules.online.cgform.model;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.List;
import java.util.Map;

public class eModel {
    private String a;

    private Map<String, Object> b;

    private Map<String, String> c;

    private List<OnlCgformField> d;

    public void setSql(String sql) {
        this.a = sql;
    }

    public void setParams(Map<String, Object> params) {
        this.b = params;
    }

    public void setTableAliasMap(Map<String, String> tableAliasMap) {
        this.c = tableAliasMap;
    }

    public void setFieldList(List<OnlCgformField> fieldList) {
        this.d = fieldList;
    }

    protected boolean a(Object paramObject) {
        return paramObject instanceof eModel;
    }


    public String getSql() {
        return this.a;
    }

    public Map<String, Object> getParams() {
        return this.b;
    }

    public Map<String, String> getTableAliasMap() {
        return this.c;
    }

    public List<OnlCgformField> getFieldList() {
        return this.d;
    }

    public eModel() {
    }

    public eModel(String paramString, Map<String, Object> paramMap) {
        this.a = paramString;
        this.b = paramMap;
    }
}
