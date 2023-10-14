package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.b;

public interface IOnlineService {
  b queryOnlineConfig(OnlCgformHead paramOnlCgformHead, String paramString);
  
  JSONObject queryOnlineFormObj(OnlCgformHead paramOnlCgformHead, OnlCgformEnhanceJs paramOnlCgformEnhanceJs);
  
  JSONObject queryOnlineFormObj(OnlCgformHead paramOnlCgformHead, String paramString);
  
  List<OnlCgformButton> queryFormValidButton(String paramString);
  
  JSONObject queryOnlineFormItem(OnlCgformHead paramOnlCgformHead, String paramString);
  
  JSONObject queryFlowOnlineFormItem(OnlCgformHead paramOnlCgformHead, String paramString1, String paramString2);
  
  String queryEnahcneJsString(String paramString1, String paramString2);
  
  JSONObject getOnlineVue3QueryInfo(String paramString);
  
  List<DictModel> getOnlineTableDictData(String paramString1, String paramString2, String paramString3);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\IOnlineService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */