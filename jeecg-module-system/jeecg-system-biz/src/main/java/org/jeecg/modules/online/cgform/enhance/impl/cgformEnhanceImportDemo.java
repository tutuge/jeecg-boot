package org.jeecg.modules.online.cgform.enhance.impl;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaImportInter;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cgformEnhanceImportDemo")
public class cgformEnhanceImportDemo implements CgformEnhanceJavaImportInter {
    private static final Logger a = LoggerFactory.getLogger(cgformEnhanceImportDemo.class);

    public EnhanceDataEnum execute(String tableName, JSONObject json) throws BusinessException {
        if (oConvertUtils.isEmpty(json.get("name"))) {
            json.put("name", "默认值");
            return EnhanceDataEnum.INSERT;
        }
        if ("error".equals(json.getString("name"))) {
            json.put("name", "默认值");
            throw new BusinessException("测试抛出异常error");
        }
        if ("hello".equals(json.getString("name"))) {
            json.put("id", "testid123");
            json.put("name", "JAVA导入增强 测试修改");
            return EnhanceDataEnum.UPDATE;
        }
        if ("ok".equals(json.getString("name")))
            return EnhanceDataEnum.ABANDON;
        return EnhanceDataEnum.INSERT;
    }
}
