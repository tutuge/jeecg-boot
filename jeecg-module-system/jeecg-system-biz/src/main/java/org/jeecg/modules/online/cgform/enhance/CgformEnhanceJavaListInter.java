package org.jeecg.modules.online.cgform.enhance;

import org.jeecg.modules.online.config.exception.BusinessException;

import java.util.List;
import java.util.Map;

public interface CgformEnhanceJavaListInter {
    void execute(String paramString, List<Map<String, Object>> paramList) throws BusinessException;
}
