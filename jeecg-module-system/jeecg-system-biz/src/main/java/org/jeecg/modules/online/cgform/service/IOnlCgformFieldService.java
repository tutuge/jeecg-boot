package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.a1.aEntity;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.model.e;

import java.util.List;
import java.util.Map;

public interface IOnlCgformFieldService extends IService<OnlCgformField> {
  Map<String, Object> queryAutolistPage(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap, List<String> paramList);

  Map<String, Object> queryAutoTreeNoPage(String paramString1, String paramString2, Map<String, Object> paramMap, List<String> paramList, String paramString3);

  void deleteAutoListMainAndSub(OnlCgformHead paramOnlCgformHead, String paramString);

  void deleteAutoListById(String paramString1, String paramString2);

  void deleteAutoList(String paramString1, String paramString2, String paramString3);

  void saveFormData(String paramString1, String paramString2, JSONObject paramJSONObject, boolean paramBoolean);

  void saveTreeFormData(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3, String paramString4);

  void saveFormData(List<OnlCgformField> paramList, String paramString, JSONObject paramJSONObject);

  List<OnlCgformField> queryFormFields(String paramString, boolean paramBoolean);

  List<OnlCgformField> queryFormFieldsByTableName(String paramString);

  OnlCgformField queryFormFieldByTableNameAndField(String paramString1, String paramString2);

  void editTreeFormData(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3, String paramString4);

  void editFormData(String paramString1, String paramString2, JSONObject paramJSONObject, boolean paramBoolean);

  Map<String, Object> queryFormData(String paramString1, String paramString2, String paramString3);

  Map<String, Object> queryFormData(List<OnlCgformField> paramList, String paramString1, String paramString2);

  Map<String, Object> generateMockData(String paramString);

  List<Map<String, Object>> querySubFormData(List<OnlCgformField> paramList, String paramString1, String paramString2, String paramString3);

  List<Map<String, String>> getAutoListQueryInfo(String paramString);

  IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString);

  List<String> selectOnlineHideColumns(String paramString);

  List<OnlCgformField> queryAvailableFields(String paramString1, String paramString2, String paramString3, boolean paramBoolean);

  List<String> queryDisabledFields(String paramString);

  List<String> queryDisabledFields(String paramString1, String paramString2);

  List<OnlCgformField> queryAvailableFields(String paramString, boolean paramBoolean, List<OnlCgformField> paramList, List<String> paramList1);

  List<OnlCgformField> queryAvailableFields(String paramString1, String paramString2, boolean paramBoolean, List<OnlCgformField> paramList, List<String> paramList1);

  void executeInsertSQL(Map<String, Object> paramMap);

  void executeUpdatetSQL(Map<String, Object> paramMap);

  List<TreeModel> queryDataListByLinkDown(aEntity parama);

  void updateTreeNodeNoChild(String paramString1, String paramString2, String paramString3);

  String queryTreeChildIds(OnlCgformHead paramOnlCgformHead, String paramString);

  String queryTreePids(OnlCgformHead paramOnlCgformHead, String paramString);

  String queryForeignKey(String paramString1, String paramString2);

  List<Map<String, Object>> queryListBySql(String paramString);

  IPage<Map<String, Object>> selectPageBySql(String paramString, int paramInt1, int paramInt2);

  void clearCacheOnlineConfig();

  e getQueryInfo(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap, List<String> paramList);

  void addOnlineInsertDataLog(String paramString1, String paramString2);

  void addOnlineUpdateDataLog(String paramString1, String paramString2, List<OnlCgformField> paramList, JSONObject paramJSONObject);

  List<Map<String, Object>> queryLinkTableDictList(String paramString1, String paramString2, String paramString3);

  void handleLinkTableDictData(String paramString, List<Map<String, Object>> paramList);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\IOnlCgformFieldService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
