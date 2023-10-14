package org.jeecg.modules.online.cgform.service;

import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.config.exception.BusinessException;

public interface IOnlCgformSqlService {
  void saveBatchOnlineTable(OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList, List<Map<String, Object>> paramList1) throws BusinessException;

  Map<String, String> saveOnlineImportDataWithValidate(OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList, List<Map<String, Object>> paramList1);

  void saveOrUpdateSubData(String paramString, OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList) throws BusinessException;
}
