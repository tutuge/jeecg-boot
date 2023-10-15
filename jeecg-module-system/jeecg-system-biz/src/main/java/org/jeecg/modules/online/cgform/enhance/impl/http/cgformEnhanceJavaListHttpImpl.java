package org.jeecg.modules.online.cgform.enhance.impl.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("cgformEnhanceJavaListHttpImpl")
public class cgformEnhanceJavaListHttpImpl implements CgformEnhanceHttpInter {
    private static final Logger a = LoggerFactory.getLogger(cgformEnhanceJavaListHttpImpl.class);

    public void execute(String tableName, List<Map<String, Object>> dataList, OnlCgformEnhanceJava enhance) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("tableName", tableName);
        jSONObject.put("dataList", dataList);
        Object object = sendPost(jSONObject, enhance);
        if (object instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) object;
            for (byte b1 = 0; b1 < dataList.size(); b1++) {
                Map map = dataList.get(b1);
                JSONObject jSONObject1 = jSONArray.getJSONObject(b1);
                map.putAll((Map) jSONObject1);
            }
        }
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enhance\impl\http\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
