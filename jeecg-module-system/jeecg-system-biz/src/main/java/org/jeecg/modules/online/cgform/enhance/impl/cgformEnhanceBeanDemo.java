package org.jeecg.modules.online.cgform.enhance.impl;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cgformEnhanceBeanDemo")
public class cgformEnhanceBeanDemo implements CgformEnhanceJavaInter {
    private static final Logger a = LoggerFactory.getLogger(cgformEnhanceBeanDemo.class);

    public void execute(String tableName, JSONObject json) throws BusinessException {
        if (json.containsKey("phone"))
            json.put("phone", "18611100000");
    }
}
