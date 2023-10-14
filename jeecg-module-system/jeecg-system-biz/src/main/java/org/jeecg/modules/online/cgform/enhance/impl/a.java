 package org.jeecg.modules.online.cgform.enhance.impl;
 
 import com.alibaba.fastjson.JSONObject;
 import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
 import org.jeecg.modules.online.config.exception.BusinessException;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Component;
 
 @Component("cgformEnhanceBeanDemo")
 public class a implements CgformEnhanceJavaInter {
   private static final Logger a = LoggerFactory.getLogger(a.class);
   
   public void execute(String tableName, JSONObject json) throws BusinessException {
     if (json.containsKey("phone"))
       json.put("phone", "18611100000"); 
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enhance\impl\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
