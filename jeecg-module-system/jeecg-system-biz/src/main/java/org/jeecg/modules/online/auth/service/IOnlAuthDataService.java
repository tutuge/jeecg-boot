package org.jeecg.modules.online.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;

public interface IOnlAuthDataService extends IService<OnlAuthData> {
  void deleteOne(String paramString);
  
  List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String paramString1, String paramString2);
  
  void createAiTestAuthData(JSONObject paramJSONObject);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\auth\service\IOnlAuthDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */