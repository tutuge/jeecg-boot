package org.jeecg.common.util.a.a1;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.a.b1;

import java.util.HashMap;
import java.util.Map;

public class aDict extends b1 {
    private static final long m = 3786503639885610767L;

    private String dictCode;

    private String dictTable;

    private String dictText;

    public String getDictCode() {
        return this.dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictTable() {
        return this.dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public String getDictText() {
        return this.dictText;
    }

    public void setDictText(String dictText) {
        this.dictText = dictText;
    }

    public aDict() {
    }

    public aDict(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
        this.b = "string";
        this.e = "sel_search";
        this.a = paramString1;
        this.f = paramString2;
        this.dictCode = paramString4;
        this.dictTable = paramString3;
        this.dictText = paramString5;
    }

    public aDict(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {

        this.b = "string";

        this.e = paramString2;

        this.a = paramString1;

        this.f = paramString3;

        this.dictCode = paramString5;

        this.dictTable = paramString4;

        this.dictText = paramString6;
    }

    public Map<String, Object> getPropertyJson() {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject jSONObject = getCommonJson();
        if (this.dictCode != null)
            jSONObject.put("dictCode", this.dictCode);
        if (this.dictTable != null)
            jSONObject.put("dictTable", this.dictTable);
        if (this.dictText != null)
            jSONObject.put("dictText", this.dictText);
        hashMap.put("prop", jSONObject);

        return (Map) hashMap;
    }
}
