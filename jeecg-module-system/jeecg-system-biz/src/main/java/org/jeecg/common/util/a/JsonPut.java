package org.jeecg.common.util.a;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonPut {
    private static final Logger a = LoggerFactory.getLogger(JsonPut.class);

    public static JSONObject a(JsonSchema paramc, List<b1> paramList) {
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("$schema", paramc.get$schema());
        jSONObject1.put("type", paramc.getType());
        jSONObject1.put("title", paramc.getTitle());
        List<String> list = paramc.getRequired();
        jSONObject1.put("required", list);
        JSONObject jSONObject2 = new JSONObject();
        for (b1 b : paramList) {
            Map<String, Object> map = b.getPropertyJson();
            jSONObject2.put(map.get("key").toString(), map.get("prop"));
        }
        jSONObject1.put("properties", jSONObject2);
        return jSONObject1;
    }

    public static JSONObject a(String paramString, List<String> paramList, List<b1> paramList1) {
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("type", "object");
        jSONObject1.put("view", "tab");
        jSONObject1.put("title", paramString);
        if (paramList == null)
            paramList = new ArrayList<>();
        jSONObject1.put("required", paramList);
        JSONObject jSONObject2 = new JSONObject();
        for (b1 b : paramList1) {
            Map<String, Object> map = b.getPropertyJson();
            jSONObject2.put(map.get("key").toString(), map.get("prop"));
        }
        jSONObject1.put("properties", jSONObject2);
        return jSONObject1;
    }
}
