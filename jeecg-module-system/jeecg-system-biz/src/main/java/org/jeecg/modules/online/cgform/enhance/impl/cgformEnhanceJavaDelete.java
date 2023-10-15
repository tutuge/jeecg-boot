 package org.jeecg.modules.online.cgform.enhance.impl;

 import com.alibaba.fastjson.JSONObject;
 import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
 import org.jeecg.modules.online.config.exception.BusinessException;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Component;

 @Component("cgformEnhanceJavaDelete")
 public class cgformEnhanceJavaDelete implements CgformEnhanceJavaInter {
   private static final Logger a = LoggerFactory.getLogger(cgformEnhanceJavaDelete.class);

   public void execute(String tableName, JSONObject json) throws BusinessException {}
 }
