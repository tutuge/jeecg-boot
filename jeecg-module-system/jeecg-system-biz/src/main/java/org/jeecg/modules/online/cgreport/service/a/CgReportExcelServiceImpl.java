package org.jeecg.modules.online.cgreport.service.a;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jeecg.modules.online.cgreport.service.CgReportExcelServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("cgReportExcelService")
public class CgReportExcelServiceImpl implements CgReportExcelServiceI {
    private static final Logger a = LoggerFactory.getLogger(CgReportExcelServiceImpl.class);

    public HSSFWorkbook exportExcel(String title, Collection<?> titleSet, Collection<?> dataSet) {
        HSSFWorkbook hSSFWorkbook = null;
        try {
            if (titleSet == null || titleSet.size() == 0)
                throw new Exception("读取表头失败！");
            if (title == null)
                title = "";
            hSSFWorkbook = new HSSFWorkbook();
            HSSFSheet hSSFSheet = hSSFWorkbook.createSheet(title);
            byte b1 = 0;
            byte b2 = 0;
            Row row = hSSFSheet.createRow(b1);
            row.setHeight((short) 450);
            HSSFCellStyle hSSFCellStyle1 = a(hSSFWorkbook);
            List<Map> list = (List) titleSet;
            Iterator<?> iterator = dataSet.iterator();
            for (Map map : list) {
                String str = (String) map.get("field_txt");
                Cell cell = row.createCell(b2);
                HSSFRichTextString hSSFRichTextString = new HSSFRichTextString(str);
                cell.setCellValue(hSSFRichTextString);
                cell.setCellStyle(hSSFCellStyle1);
                b2++;
            }
            HSSFCellStyle hSSFCellStyle2 = c(hSSFWorkbook);
            while (iterator.hasNext()) {
                b2 = 0;
                b1++;
                row = hSSFSheet.createRow(b1);
                Map map = (Map) iterator.next();
                for (Map map1 : list) {
                    String str1 = (String) map1.get("field_name");
                    String str2 = (map.get(str1) == null) ? "" : map.get(str1).toString();
                    Cell cell = row.createCell(b2);
                    HSSFRichTextString hSSFRichTextString = new HSSFRichTextString(str2);
                    cell.setCellStyle((CellStyle) hSSFCellStyle2);
                    cell.setCellValue((RichTextString) hSSFRichTextString);
                    b2++;
                }
            }
            for (byte b3 = 0; b3 < list.size(); b3++)
                hSSFSheet.autoSizeColumn(b3);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
        }
        return hSSFWorkbook;
    }

    private HSSFCellStyle a(HSSFWorkbook paramHSSFWorkbook) {
        HSSFCellStyle hSSFCellStyle = paramHSSFWorkbook.createCellStyle();
        hSSFCellStyle.setBorderLeft(BorderStyle.THIN);
        hSSFCellStyle.setBorderRight(BorderStyle.THIN);
        hSSFCellStyle.setBorderBottom(BorderStyle.THIN);
        hSSFCellStyle.setBorderTop(BorderStyle.THIN);
        hSSFCellStyle.setAlignment(HorizontalAlignment.CENTER);
        hSSFCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        hSSFCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return hSSFCellStyle;
    }

    private void a(int paramInt1, int paramInt2, HSSFWorkbook paramHSSFWorkbook) {
        HSSFSheet hSSFSheet = paramHSSFWorkbook.getSheetAt(0);
        HSSFCellStyle hSSFCellStyle = c(paramHSSFWorkbook);
        for (byte b = 1; b <= paramInt1; b++) {
            Row row = hSSFSheet.createRow(b);
            for (byte b1 = 0; b1 < paramInt2; b1++)
                row.createCell(b1).setCellStyle(hSSFCellStyle);
        }
    }

    private HSSFCellStyle b(HSSFWorkbook paramHSSFWorkbook) {
        HSSFCellStyle hSSFCellStyle = paramHSSFWorkbook.createCellStyle();
        hSSFCellStyle.setBorderLeft(BorderStyle.THIN);
        hSSFCellStyle.setBorderRight(BorderStyle.THIN);
        hSSFCellStyle.setBorderBottom(BorderStyle.THIN);
        hSSFCellStyle.setBorderTop(BorderStyle.THIN);
        hSSFCellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
        hSSFCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return hSSFCellStyle;
    }

    private HSSFCellStyle c(HSSFWorkbook paramHSSFWorkbook) {
        HSSFCellStyle hSSFCellStyle = paramHSSFWorkbook.createCellStyle();
        hSSFCellStyle.setBorderLeft(BorderStyle.THIN);
        hSSFCellStyle.setBorderRight(BorderStyle.THIN);
        hSSFCellStyle.setBorderBottom(BorderStyle.THIN);
        hSSFCellStyle.setBorderTop(BorderStyle.THIN);
        return hSSFCellStyle;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\service\a\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
