package org.jeecg.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.config.exception.BusinessException;

import java.util.Map;

public interface CgformEnhanceJavaInter {
    void execute(String paramString, JSONObject paramJSONObject) throws BusinessException;

    @Deprecated
    default void execute(String tableName, Map<String, Object> map) throws BusinessException {
    }
}
