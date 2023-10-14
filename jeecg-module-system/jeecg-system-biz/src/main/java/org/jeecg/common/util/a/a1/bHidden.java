package org.jeecg.common.util.a.a1;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.a.b1;

import java.util.HashMap;
import java.util.Map;


public class bHidden extends b1 {
    private static final long m = -8939298551502162479L;

    public bHidden() {
    }

    public bHidden(String paramString1, String paramString2) {
        this.b = "string";
        this.e = "hidden";
        this.a = paramString1;
        this.f = paramString2;
    }

    public Map<String, Object> getPropertyJson() {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject jSONObject = getCommonJson();
        jSONObject.put("hidden", Boolean.TRUE);
        hashMap.put("prop", jSONObject);
        return (Map) hashMap;
    }
}
