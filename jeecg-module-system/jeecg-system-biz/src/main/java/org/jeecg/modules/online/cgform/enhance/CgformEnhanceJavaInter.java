package org.jeecg.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.jeecg.modules.online.config.exception.BusinessException;

public interface CgformEnhanceJavaInter {
  void execute(String paramString, JSONObject paramJSONObject) throws BusinessException;
  
  @Deprecated
  default void execute(String tableName, Map<String, Object> map) throws BusinessException {}
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enhance\CgformEnhanceJavaInter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */