package org.jeecg.common.util.a.a1;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.a.b1;

import java.util.HashMap;
import java.util.Map;

public class h extends b1 {
    private static final long m = 3786503639885610767L;

    private String n;

    private String o;

    private String p;

    private String q;

    private String r;

    private Integer s = 0;

    public String getDict() {
        return this.n;
    }

    public void setDict(String dict) {
        this.n = dict;
    }

    public String getPidField() {
        return this.o;
    }

    public void setPidField(String pidField) {
        this.o = pidField;
    }

    public String getPidValue() {
        return this.p;
    }

    public void setPidValue(String pidValue) {
        this.p = pidValue;
    }

    public String getHasChildField() {
        return this.q;
    }

    public void setHasChildField(String hasChildField) {
        this.q = hasChildField;
    }

    public h() {
    }

    public String getTextField() {
        return this.r;
    }

    public void setTextField(String textField) {
        this.r = textField;
    }

    public Integer getPidComponent() {
        return this.s;
    }

    public void setPidComponent(Integer pidComponent) {
        this.s = pidComponent;
    }

    public h(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
        this.b = "string";
        this.e = "sel_tree";
        this.a = paramString1;
        this.f = paramString2;
        this.n = paramString3;
        this.o = paramString4;
        this.p = paramString5;
    }

    public h(String paramString1, String paramString2, String paramString3) {
        this.b = "string";
        this.e = "cat_tree";
        this.a = paramString1;
        this.f = paramString2;
        this.p = paramString3;
    }

    public h(String paramString1, String paramString2, String paramString3, String paramString4) {
        this(paramString1, paramString2, paramString3);
        this.r = paramString4;
    }

    public Map<String, Object> getPropertyJson() {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject jSONObject = getCommonJson();
        if (this.n != null)
            jSONObject.put("dict", this.n);
        if (this.o != null)
            jSONObject.put("pidField", this.o);
        if (this.p != null)
            jSONObject.put("pidValue", this.p);
        if (this.r != null)
            jSONObject.put("textField", this.r);
        if (this.q != null)
            jSONObject.put("hasChildField", this.q);
        if (this.s != null)
            jSONObject.put("pidComponent", this.s);
        hashMap.put("prop", jSONObject);
        return (Map) hashMap;
    }
}
