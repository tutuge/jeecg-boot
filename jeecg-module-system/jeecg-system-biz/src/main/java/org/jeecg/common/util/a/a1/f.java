package org.jeecg.common.util.a.a1;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.a.b1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class f extends b1 {
    private static final long m = -3200493311633999539L;

    private Integer n;

    private Integer o;

    private String p;

    private String q;

    public Integer getMaxLength() {
        return this.n;
    }

    public void setMaxLength(Integer maxLength) {
        this.n = maxLength;
    }

    public Integer getMinLength() {
        return this.o;
    }

    public void setMinLength(Integer minLength) {
        this.o = minLength;
    }

    public String getPattern() {
        return this.p;
    }

    public void setPattern(String pattern) {
        this.p = pattern;
    }

    public String getErrorInfo() {
        return this.q;
    }

    public void setErrorInfo(String errorInfo) {
        this.q = errorInfo;
    }

    public f() {
    }

    public f(String paramString1, String paramString2, String paramString3, Integer paramInteger) {
        this.n = paramInteger;
        this.a = paramString1;
        this.e = paramString3;
        this.f = paramString2;
        this.b = "string";
    }

    public f(String paramString1, String paramString2, String paramString3, Integer paramInteger, List<DictModel> paramList) {
        this.n = paramInteger;
        this.a = paramString1;
        this.e = paramString3;
        this.f = paramString2;
        this.b = "string";
        this.c = paramList;
    }

    public Map<String, Object> getPropertyJson() {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject jSONObject = getCommonJson();
        if (this.n != null)
            jSONObject.put("maxLength", this.n);
        if (this.o != null)
            jSONObject.put("minLength", this.o);
        if (this.p != null)
            jSONObject.put("pattern", this.p);
        if (this.q != null)
            jSONObject.put("errorInfo", this.q);
        hashMap.put("prop", jSONObject);
        return (Map) hashMap;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\commo\\util\a\a\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
