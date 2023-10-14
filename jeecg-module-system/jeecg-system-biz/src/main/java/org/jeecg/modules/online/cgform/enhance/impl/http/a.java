 package org.jeecg.modules.online.cgform.enhance.impl.http;
 
 import com.alibaba.fastjson.JSONObject;
 import java.util.Map;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.enhance.impl.http.base.CgformEnhanceHttpInter;
 import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Component;
 
 @Component("cgformEnhanceJavaHttpImpl")
 public class a implements CgformEnhanceHttpInter {
   private static final Logger a = LoggerFactory.getLogger(a.class);
   
   public void execute(String tableName, JSONObject record, OnlCgformEnhanceJava enhance) {
     JSONObject jSONObject = new JSONObject();
     jSONObject.put("tableName", tableName);
     jSONObject.put("record", record);
     Object object = sendPost(jSONObject, enhance);
     Integer integer = null;
     if (object != null) {
       integer = oConvertUtils.getInt(object);
       if (integer == null && object instanceof JSONObject) {
         JSONObject jSONObject1 = (JSONObject)object;
         integer = oConvertUtils.getInt(jSONObject1.get("code"));
         JSONObject jSONObject2 = jSONObject1.getJSONObject("record");
         if (jSONObject2 != null)
           record.putAll((Map)jSONObject2); 
       } 
     } 
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enhance\impl\http\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
