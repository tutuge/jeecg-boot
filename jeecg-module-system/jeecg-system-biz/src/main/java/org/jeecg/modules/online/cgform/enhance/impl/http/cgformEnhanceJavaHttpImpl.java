package org.jeecg.modules.online.cgform.enhance.impl.http;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

    @Component("cgformEnhanceJavaHttpImpl")
public class cgformEnhanceJavaHttpImpl implements CgformEnhanceHttpInter {
    private static final Logger a = LoggerFactory.getLogger(cgformEnhanceJavaHttpImpl.class);

    public void execute(String tableName, JSONObject record, OnlCgformEnhanceJava enhance) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("tableName", tableName);
        jSONObject.put("record", record);
        Object object = sendPost(jSONObject, enhance);
        Integer integer = null;
        if (object != null) {
            integer = oConvertUtils.getInt(object);
            if (integer == null && object instanceof JSONObject) {
                JSONObject jSONObject1 = (JSONObject) object;
                integer = oConvertUtils.getInt(jSONObject1.get("code"));
                JSONObject jSONObject2 = jSONObject1.getJSONObject("record");
                if (jSONObject2 != null)
                    record.putAll(jSONObject2);
            }
        }
    }
}
