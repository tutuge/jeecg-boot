package org.jeecg.modules.online.config.bAttribute;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;

public class eTableConfig {
    private String a;

    private String b;

    private String c;

    private String d;

    private String e;

    private String f;

    private Integer g;

    private String h;

    private String i;

    private String j;

    public void setName(String name) {
        this.a = name;
    }

    public void setType(String type) {
        this.b = type;
    }

    public void setView(String view) {
        this.c = view;
    }

    public void setMode(String mode) {
        this.d = mode;
    }

    public void setVal(String val) {
        this.e = val;
    }

    public void setRule(String rule) {
        this.f = rule;
    }

    public void setIsSearch(Integer isSearch) {
        this.g = isSearch;
    }

    public void setMainField(String mainField) {
        this.h = mainField;
    }

    public void setMainTable(String mainTable) {
        this.i = mainTable;
    }

    public void setTable(String table) {
        this.j = table;
    }

    protected boolean a(Object paramObject) {
        return paramObject instanceof eTableConfig;
    }


    public String getName() {
        return this.a;
    }

    public String getType() {
        return this.b;
    }

    public String getView() {
        return this.c;
    }

    public String getMode() {
        return this.d;
    }

    public String getVal() {
        return this.e;
    }

    public String getRule() {
        return this.f;
    }

    public Integer getIsSearch() {
        return this.g;
    }

    public String getMainField() {
        return this.h;
    }

    public String getMainTable() {
        return this.i;
    }

    public String getTable() {
        return this.j;
    }

    public eTableConfig() {
    }

    public eTableConfig(JSONObject paramJSONObject) {
        String str1 = paramJSONObject.getString("field");
        String[] arrayOfString = str1.split(",");
        if (arrayOfString.length == 1) {
            this.a = str1;
        } else if (arrayOfString.length == 2) {
            this.a = arrayOfString[1];
            this.j = arrayOfString[0];
        }
        String str2 = paramJSONObject.getString("type");
        String str3 = paramJSONObject.getString("dbType");
        if (oConvertUtils.isNotEmpty(str3) && g.a(str3)) {
            this.b = str3;
        } else {
            this.b = str2;
        }
        if ("list_multi".equals(str2) || "popup".equals(str2))
            this.c = str2;
        this.f = paramJSONObject.getString("rule");
        this.e = paramJSONObject.getString("val");
        this.d = "single";
        this.g = Integer.valueOf(1);
    }

    public eTableConfig(OnlCgreportItem paramOnlCgreportItem) {
        this.a = paramOnlCgreportItem.getFieldName();
        this.b = paramOnlCgreportItem.getFieldType();
        this.d = paramOnlCgreportItem.getSearchMode();
        this.g = paramOnlCgreportItem.getIsSearch();
    }

    public eTableConfig(OnlCgformField paramOnlCgformField) {
        this.a = paramOnlCgformField.getDbFieldName();
        this.b = paramOnlCgformField.getDbType();
        this.d = paramOnlCgformField.getQueryMode();
        this.g = paramOnlCgformField.getIsQuery();
        this.h = paramOnlCgformField.getMainField();
        this.i = paramOnlCgformField.getMainTable();
        String str = paramOnlCgformField.getQueryConfigFlag();
        if ("1".equals(str)) {
            this.c = paramOnlCgformField.getQueryShowType();
        } else {
            this.c = paramOnlCgformField.getFieldShowType();
        }
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\b\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
