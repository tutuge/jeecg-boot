package org.jeecg.modules.online.cgreport.service;

import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;

public interface IOnlCgreportAPIService {
  Map<String, Object> getDataById(String paramString, Map<String, Object> paramMap);
  
  Map<String, Object> getDataByCode(String paramString, Map<String, Object> paramMap);
  
  Map<String, Object> getData(String paramString1, String paramString2, Map<String, Object> paramMap);
  
  Map<String, Object> executeSelectSqlRoute(String paramString1, String paramString2, Map<String, Object> paramMap, String paramString3) throws Exception;
  
  Workbook getReportWorkbook(String paramString, Map<String, Object> paramMap);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\service\IOnlCgreportAPIService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */