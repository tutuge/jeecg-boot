package org.jeecg.modules.online.cgreport.service;

import java.util.Collection;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface CgReportExcelServiceI {
  HSSFWorkbook exportExcel(String paramString, Collection<?> paramCollection1, Collection<?> paramCollection2);
}
