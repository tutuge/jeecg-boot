package org.jeecg.modules.online.cgform.service;

import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.e;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;

public interface IOnlineJoinQueryService {
  Map<String, Object> pageList(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap, boolean paramBoolean);

  Map<String, Object> pageList(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap);

  e getQueryInfo(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap, boolean paramBoolean);

  e getQueryInfo(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap, boolean paramBoolean1, boolean paramBoolean2);

  XSSFWorkbook handleOnlineExport(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap);

  void addAllSubTableDate(String paramString, Map<String, Object> paramMap, List<Map<String, Object>> paramList, List<ExcelExportEntity> paramList1, boolean paramBoolean);
}
