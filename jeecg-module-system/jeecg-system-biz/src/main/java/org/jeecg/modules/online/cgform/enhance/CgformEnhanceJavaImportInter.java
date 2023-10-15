package org.jeecg.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.config.exception.BusinessException;

public interface CgformEnhanceJavaImportInter {
    EnhanceDataEnum execute(String paramString, JSONObject paramJSONObject) throws BusinessException;
}
