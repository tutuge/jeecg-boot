package org.jeecg.common.util.a.a1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.a.b1;

import java.util.HashMap;
import java.util.Map;

public class PropertyJson extends b1 {
    private String m;

    public PropertyJson() {
    }

    public PropertyJson(String paramString1, String paramString2, String paramString3) {
        this.b = "string";
        this.e = "switch";
        this.a = paramString1;
        this.f = paramString2;
        this.m = paramString3;
    }

    public Map<String, Object> getPropertyJson() {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("key", getKey());
        JSONObject jSONObject = getCommonJson();
        JSONArray jSONArray = new JSONArray();
        if (this.m != null) {
            jSONArray = JSONArray.parseArray(this.m);
            jSONObject.put("extendOption", jSONArray);
        }
        hashMap.put("prop", jSONObject);
        return (Map) hashMap;
    }
}
