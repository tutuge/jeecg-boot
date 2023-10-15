package org.jeecg.modules.online.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;

import java.util.List;

public interface IOnlAuthDataService extends IService<OnlAuthData> {
  void deleteOne(String paramString);

  List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String paramString1, String paramString2);

  void createAiTestAuthData(JSONObject paramJSONObject);
}
