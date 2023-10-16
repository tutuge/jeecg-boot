package org.jeecg.modules.online.cgreport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.config.c.OnlReportQueryBlackListHandler;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("onlCgreportAPIService")
public class OnlCgreportAPIServiceImpl implements IOnlCgreportAPIService {
    private static final Logger a = LoggerFactory.getLogger(OnlCgreportAPIServiceImpl.class);

    @Autowired
    private OnlCgreportHeadServiceImpl onlCgreportHeadService;

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @Autowired
    private OnlReportQueryBlackListHandler onlReportQueryBlackListHandler;

    public Map<String, Object> getDataById(String id, Map<String, Object> params) {
        return getData(id, null, params);
    }

    public Map<String, Object> getDataByCode(String code, Map<String, Object> params) {
        return getData(null, code, params);
    }

    public Map<String, Object> getData(String id, String code, Map<String, Object> params) {
        OnlCgreportHead onlCgreportHead = null;
        if (oConvertUtils.isNotEmpty(id)) {
            onlCgreportHead = this.onlCgreportHeadService.getById(id);
        } else if (oConvertUtils.isNotEmpty(code)) {
            LambdaQueryWrapper<OnlCgreportHead> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgreportHead.class)
            .eq(OnlCgreportHead::getCode, code);
            onlCgreportHead = this.onlCgreportHeadService.getOne(lambdaQueryWrapper);
        }
        if (onlCgreportHead == null)
            throw new JeecgBootException("实体不存在");
        try {
            String str1 = onlCgreportHead.getCgrSql().trim();
            String str2 = onlCgreportHead.getDbSource();
            return executeSelectSqlRoute(str2, str1, params, onlCgreportHead.getId());
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            throw new JeecgBootException("SQL执行失败：" + exception.getMessage());
        }
    }

    public Map<String, Object> executeSelectSqlRoute(String dbKey, String sql, Map<String, Object> params, String headId) throws Exception {
        if (!this.onlReportQueryBlackListHandler.isPass(sql))
            throw new JeecgBootException(this.onlReportQueryBlackListHandler.getError());
        if (StringUtils.isNotBlank(dbKey))
            return this.onlCgreportHeadService.executeSelectSqlDynamic(dbKey, sql, params, headId);
        return this.onlCgreportHeadService.executeSelectSql(sql, headId, params);
    }

    public Workbook getReportWorkbook(String reportId, Map<String, Object> params) {
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgreportItem.class)
                .eq(OnlCgreportItem::getCgrheadId, reportId)
                .orderByAsc(OnlCgreportItem::getOrderNum);
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(lambdaQueryWrapper);
        ArrayList<ExcelExportEntity> arrayList = new ArrayList<>();
        HashMap<Object, Object> hashMap1 = new HashMap<>(5);
        ArrayList<String> arrayList1 = new ArrayList<>();
        HashMap<Object, Object> hashMap2 = new HashMap<>(5);
        for (OnlCgreportItem onlCgreportItem : list) {
            String str1 = onlCgreportItem.getFieldType();
            String str2 = onlCgreportItem.getFieldName();
            if ("1".equals(oConvertUtils.getString(onlCgreportItem.getIsShow()))) {
                ExcelExportEntity excelExportEntity = new ExcelExportEntity(onlCgreportItem.getFieldTxt(), str2, 15);
                a(onlCgreportItem, excelExportEntity);
                if ("date".equalsIgnoreCase(onlCgreportItem.getFieldType()))
                    excelExportEntity.setFormat("yyyy-MM-dd");
                String str = onlCgreportItem.getGroupTitle();
                if (oConvertUtils.isNotEmpty(str)) {
                    List<String> list1 = new ArrayList<>();
                    if (hashMap1.containsKey(str)) {
                        list1 = (List) hashMap1.get(str);
                        list1.add(str2);
                    } else {
                        ExcelExportEntity excelExportEntity1 = new ExcelExportEntity(str, str, true);
                        arrayList.add(excelExportEntity1);
                        list1.add(str2);
                    }
                    hashMap1.put(str, list1);
                    excelExportEntity.setColspan(true);
                }
                if (oConvertUtils.isNotEmpty(str1))
                    if (oConvertUtils.isEmpty(onlCgreportItem.getDictCode()) && ("Integer".equals(str1) || "Long".equals(str1)))
                        excelExportEntity.setType(4);
                arrayList.add(excelExportEntity);
            }
            if ("1".equals(oConvertUtils.getString(onlCgreportItem.getIsTotal())))
                arrayList1.add(str2);
        }
        for (Map.Entry<Object, Object> entry : hashMap1.entrySet()) {
            String str = (String) entry.getKey();
            List list1 = (List) entry.getValue();
            for (ExcelExportEntity excelExportEntity : arrayList) {
                if (str.equals(excelExportEntity.getName()) && excelExportEntity.isColspan())
                    excelExportEntity.setSubColumnList(list1);
            }
        }
        HSSFWorkbook hSSFWorkbook = new HSSFWorkbook();
        boolean bool = true;
        Integer integer = 1;
        params.put("pageSize", Integer.valueOf(10000));
        while (bool) {
            Integer integer1 = integer, integer2 = integer = Integer.valueOf(integer.intValue() + 1);
            params.put("pageNo", integer1);
            Map<String, Object> map = getDataById(reportId, params);
            List<HashMap<Object, Object>> list1 = (List) map.get("records");
            if (list1 == null || list1.size() == 0) {
                bool = false;
                continue;
            }
            if (arrayList1 != null && arrayList1.size() > 0) {
                for (String str : arrayList1) {
                    BigDecimal bigDecimal = new BigDecimal(0.0D);
                    for (Map map1 : list1) {
                        String str1 = map1.get(str).toString();
                        if (str1.matches("-?\\d+(.\\d+)?")) {
                            BigDecimal bigDecimal1 = new BigDecimal(str1);
                            bigDecimal = bigDecimal.add(bigDecimal1);
                        }
                    }
                    hashMap2.put(str, bigDecimal);
                }
                list1.add(hashMap2);
            }
            ExcelExportServer excelExportServer = new ExcelExportServer();
            ExportParams exportParams = new ExportParams();
            excelExportServer.createSheetForMap((Workbook) hSSFWorkbook, exportParams, arrayList, list1);
        }
        return (Workbook) hSSFWorkbook;
    }

    private void a(OnlCgreportItem paramOnlCgreportItem, ExcelExportEntity paramExcelExportEntity) {
        String str1 = "_";
        String str2 = "---";
        String str3 = paramOnlCgreportItem.getDictCode();
        List<DictModel> list = this.onlCgreportHeadService.queryColumnDictList(oConvertUtils.getString(str3), (List<Map<String, Object>>) null, (String) null);
        if (list != null && list.size() > 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (DictModel dictModel : list) {
                if (dictModel == null || dictModel.getValue() == null)
                    continue;
                if (dictModel.getValue().contains(str1)) {
                    String str = dictModel.getValue().replace(str1, str2);
                    arrayList.add(dictModel.getText() + str1 + str);
                    continue;
                }
                arrayList.add(dictModel.getText() + str1 + dictModel.getValue());
            }
            paramExcelExportEntity.setReplace(arrayList.<String>toArray(new String[arrayList.size()]));
        }
        String str4 = paramOnlCgreportItem.getReplaceVal();
        if (oConvertUtils.isNotEmpty(str4))
            paramExcelExportEntity.setReplace(str4.toString().split(","));
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\service\a\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
