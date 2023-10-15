 package org.jeecg.modules.online.cgform.enhance.impl;

 import com.alibaba.fastjson.JSONObject;
 import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
 import org.jeecg.modules.online.config.exception.BusinessException;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Component;

 @Component("cgformEnhanceJavaDemo")
 public class cgformEnhanceJavaDemo implements CgformEnhanceJavaInter {
   private static final Logger a = LoggerFactory.getLogger(cgformEnhanceJavaDemo.class);

   public void execute(String tableName, JSONObject json) throws BusinessException {}
 }
