package org.jeecg.modules.cable.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExcelUtils {

    private final static String excel2003L = ".xls";    // 2003- 版本的excel
    private final static String excel2007U = ".xlsx";   // 2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     */
    public List<List<Object>> getListByExcel(InputStream in, String fileName) throws Exception {
        return getListByExcel(in, fileName, false);
    }

    /**
     * @param in
     * @param fileName
     * @param firstRow 是否包含第一行数据
     * @return
     * @throws Exception
     */
    public List<List<Object>> getListByExcel(InputStream in, String fileName, Boolean firstRow) throws Exception {
        List<List<Object>> list;
        // 创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet;  // 页数
        Row row;  // 行数
        Cell cell;  // 列数
        list = new ArrayList<>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 遍历当前sheet中的所有行
            int firstRowNum = sheet.getFirstRowNum();
            for (int j = firstRowNum; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (!firstRow && row.getFirstCellNum() == j) {
                    continue;
                }
                // 遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
        return list;
    }


    /*描述：根据文件后缀，自适应上传文件的版本*/
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /*描述：对表格中数值进行格式化*/
    // 解决excel类型问题，获得数值
    public String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            // 数值型
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果是date类型则 ，获取该cell的date值
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(date);
                } else {// 纯数字
                    BigDecimal big = BigDecimal.valueOf(cell.getNumericCellValue());
                    value = big.toString();
                    // 解决1234.0  去掉后面的.0
                    if (null != value && !value.trim().isEmpty()) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
            }
            // 字符串类型
            case STRING -> value = cell.getStringCellValue();

            // 公式类型
            case FORMULA -> {
                // 读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if ("NaN".equals(value)) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue();
                }
            }
            // 布尔类型
            case BOOLEAN -> value = " " + cell.getBooleanCellValue();
            default -> value = cell.getStringCellValue();
        }
        assert value != null;
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }

}

