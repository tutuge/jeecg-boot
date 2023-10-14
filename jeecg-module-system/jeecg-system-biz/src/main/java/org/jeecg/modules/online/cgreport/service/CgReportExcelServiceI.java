package org.jeecg.modules.online.cgreport.service;

import java.util.Collection;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface CgReportExcelServiceI {
  HSSFWorkbook exportExcel(String paramString, Collection<?> paramCollection1, Collection<?> paramCollection2);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\service\CgReportExcelServiceI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */