package org.jeecg.modules.online.cgreport.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.BrowserUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.security.JdbcSecurityUtil;
import org.jeecg.modules.online.cgreport.c.c;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportHeadMapper;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.cgreport.service.a.OnlCgreportHeadServiceImpl;
import org.jeecg.modules.online.cgreport.service.a.OnlCgreportAPIServiceImpl;
import org.jeecg.modules.online.config.c.OnlReportQueryBlackListHandler;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("onlCgreportAPI")
@RequestMapping({"/online/cgreport/api"})
public class OnlCgreportAPI {
    private static final Logger a = LoggerFactory.getLogger(OnlCgreportAPI.class);

    @Autowired
    private OnlCgreportAPIServiceImpl cgreportAPIService;

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

    @GetMapping({"/getColumnsAndData/{code}"})
    @PermissionData
    public Result<?> a(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(paramString);
        if (onlCgreportHead == null)
            return Result.error("实体不存在");
        Result<?> result = b(paramString, paramHttpServletRequest);
        if (result.getCode().equals(Integer.valueOf(200))) {
            Map map = (Map) result.getResult();
            List list = (List) map.get("records");
            Map<String, Object> map1 = this.onlCgreportHeadService.queryColumnInfo(paramString, false);
            JSONArray jSONArray = (JSONArray) map1.get("columns");
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            if (jSONArray != null)
                for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(b1);
                    Object object = jSONObject.get("dictCode");
                    if (object != null) {
                        String str1 = object.toString();
                        String str2 = jSONArray.getJSONObject(b1).getString("dataIndex");
                        List list1 = this.onlCgreportHeadService.queryColumnDictList(str1, list, str2);
                        if (list1 != null) {
                            hashMap.put(str2, list1);
                            jSONObject.put("customRender", str2);
                        }
                    }
                }
            map1.put("cgreportHeadName", onlCgreportHead.getName());
            map1.put("data", result.getResult());
            map1.put("dictOptions", hashMap);
            return Result.ok(map1);
        }
        return result;
    }

    @Deprecated
    @GetMapping({"/getColumns/{code}"})
    public Result<?> a(@PathVariable("code") String paramString) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(paramString);
        if (onlCgreportHead == null)
            return Result.error("实体不存在");
        QueryWrapper<OnlCgreportItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", paramString);
        queryWrapper.eq("is_show", 1);
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        ArrayList<HashMap<Object, Object>> arrayList = new ArrayList<>();
        HashMap<Object, Object> hashMap1 = new HashMap<>(5);
        for (OnlCgreportItem onlCgreportItem : list) {
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            hashMap.put("title", onlCgreportItem.getFieldTxt());
            hashMap.put("dataIndex", onlCgreportItem.getFieldName());
            hashMap.put("align", "center");
            hashMap.put("sorter", "true");
            arrayList.add(hashMap);
            String str = onlCgreportItem.getDictCode();
            if (oConvertUtils.isNotEmpty(str)) {
                List list1 = null;
                if (str.toLowerCase().indexOf("select ") == 0) {
                    List list2 = ((OnlCgreportHeadMapper) this.onlCgreportHeadService.getBaseMapper()).executeSelete(str);
                    if (list2 != null && list2.size() != 0) {
                        String str1 = JSON.toJSONString(list2);
                        list1 = JSON.parseArray(str1, DictModel.class);
                    }
                } else {
                    list1 = this.sysBaseAPI.queryDictItemsByCode(str);
                }
                if (list1 != null) {
                    hashMap1.put(onlCgreportItem.getFieldName(), list1);
                    hashMap.put("customRender", onlCgreportItem.getFieldName());
                }
            }
        }
        HashMap<Object, Object> hashMap2 = new HashMap<>(1);
        hashMap2.put("columns", arrayList);
        hashMap2.put("dictOptions", hashMap1);
        hashMap2.put("cgreportHeadName", onlCgreportHead.getName());
        return Result.ok(hashMap2);
    }

    @GetMapping({"/getData/{code}"})
    @PermissionData
    public Result<?> b(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        Map<String, Object> map = c.a(paramHttpServletRequest);
        map.put("getAll", paramHttpServletRequest.getAttribute("getAll"));
        try {
            return Result.OK(this.cgreportAPIService.getDataById(paramString, map));
        } catch (JeecgBootException jeecgBootException) {
            return Result.error(jeecgBootException.getMessage());
        }
    }

    @GetMapping({"/getDataOrderByValue/{code}"})
    @PermissionData
    public Result<?> c(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getById(paramString);
        if (onlCgreportHead == null)
            return Result.error("实体不存在");
        String str1 = onlCgreportHead.getCgrSql().trim();
        String str2 = onlCgreportHead.getDbSource();
        try {
            Map<String, Object> map = c.a(paramHttpServletRequest);
            Object object1 = map.get("order_field");
            Object object2 = map.get("order_value");
            if (oConvertUtils.isEmpty(object1) || oConvertUtils.isEmpty(object2))
                return Result.error("order_field 和 order_value 参数不能为空！");
            String str = "force_" + object1;
            map.put(str, object2);
            map.put("getAll", Boolean.valueOf(true));
            Map map1 = this.cgreportAPIService.executeSelectSqlRoute(str2, str1, map, onlCgreportHead.getId());
            JSONArray jSONArray1 = JSON.parseArray(JSON.toJSONString(map1.get("records")));
            map.remove(object1.toString());
            map.remove(str);
            map.remove("order_field");
            map.remove("order_value");
            map.put("getAll", paramHttpServletRequest.getAttribute("getAll"));
            Map<String, Object> map2 = this.cgreportAPIService.executeSelectSqlRoute(str2, str1, map, onlCgreportHead.getId());
            JSONArray jSONArray2 = JSON.parseArray(JSON.toJSONString(map2.get("records")));
            a(jSONArray1, jSONArray2);
            map2.put("records", jSONArray2);
            return Result.ok(map2);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("SQL执行失败：" + exception.getMessage());
        }
    }

    private void a(JSONArray paramJSONArray1, JSONArray paramJSONArray2) {
        for (byte b1 = 0; b1 < paramJSONArray1.size(); b1++) {
            JSONObject jSONObject = paramJSONArray1.getJSONObject(b1);
            String str = jSONObject.getString("id");
            int i = (int) paramJSONArray2.stream().filter(paramObject -> paramString.equals(((JSONObject) paramObject).getString("id"))).count();
            if (i == 0)
                paramJSONArray2.add(0, jSONObject);
        }
    }

    @GetMapping({"/getQueryInfo/{code}"})
    public Result<?> b(@PathVariable("code") String paramString) {
        try {
            List list = this.onlCgreportItemService.getAutoListQueryInfo(paramString);
            return Result.ok(list);
        } catch (Exception exception) {
            return Result.error("查询失败");
        }
    }

    @GetMapping({"/getParamsInfo/{code}"})
    public Result<?> c(@PathVariable("code") String paramString) {
        try {
            LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, paramString);
            lambdaQueryWrapper.orderByAsc(OnlCgreportParam::getOrderNum);
            List list = this.onlCgreportParamService.list((Wrapper) lambdaQueryWrapper);
            return Result.ok(list);
        } catch (Exception exception) {
            return Result.error("查询失败");
        }
    }

    @PermissionData
    @RequestMapping({"/exportManySheetXls/{reportId}"})
    public void a(@PathVariable("reportId") String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
        if (oConvertUtils.isEmpty(paramString))
            throw new JeecgBootException("参数错误");
        Map map = c.a(paramHttpServletRequest);
        Workbook workbook = this.cgreportAPIService.getReportWorkbook(paramString, map);
        String str = "报表";
        paramHttpServletResponse.setContentType("application/vnd.ms-excel");
        ServletOutputStream servletOutputStream = null;
        try {
            String str1 = BrowserUtils.checkBrowse(paramHttpServletRequest);
            if ("MSIE".equalsIgnoreCase(str1.substring(0, 4))) {
                paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str, "UTF-8") + ".xls");
            } else {
                String str2 = new String(str.getBytes("UTF-8"), "ISO8859-1");
                paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + str2 + ".xls");
            }
            servletOutputStream = paramHttpServletResponse.getOutputStream();
            workbook.write((OutputStream) servletOutputStream);
        } catch (Exception exception) {
            a.warn("导出失败", exception.getMessage());
        } finally {
            try {
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (Exception exception) {
            }
        }
    }

    @Deprecated
    @PermissionData
    @RequestMapping({"/exportXls/{reportId}"})
    public void b(@PathVariable("reportId") String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
        String str1 = "报表";
        String str2 = "导出信息";
        if (oConvertUtils.isNotEmpty(paramString)) {
            Map map = null;
            try {
                map = this.onlCgreportHeadService.queryCgReportConfig(paramString);
            } catch (Exception exception) {
                throw new JeecgBootException("动态报表配置不存在!");
            }
            List<Map> list = (List) map.get("items");
            paramHttpServletRequest.setAttribute("getAll", Boolean.valueOf(true));
            Result<?> result = b(paramString, paramHttpServletRequest);
            List<HashMap<Object, Object>> list1 = null;
            if (result.getCode().equals(Integer.valueOf(200))) {
                Map map1 = (Map) result.getResult();
                list1 = (List) map1.get("records");
            }
            ArrayList<String> arrayList = new ArrayList<>();
            HashMap<Object, Object> hashMap1 = new HashMap<>(5);
            HashMap<Object, Object> hashMap2 = new HashMap<>(5);
            ArrayList<ExcelExportEntity> arrayList1 = new ArrayList<>();
            for (byte b1 = 0; b1 < list.size(); b1++) {
                Map map1 = list.get(b1);
                String str = (String) map1.get("field_type");
                if ("1".equals(oConvertUtils.getString(((Map) list.get(b1)).get("is_show")))) {
                    String str3 = ((Map) list.get(b1)).get("field_name").toString();
                    ExcelExportEntity excelExportEntity = new ExcelExportEntity(((Map) list.get(b1)).get("field_txt").toString(), str3, 15);
                    String str4 = "_";
                    String str5 = "---";
                    Object object1 = ((Map) list.get(b1)).get("dict_code");
                    List list2 = this.onlCgreportHeadService.queryColumnDictList(oConvertUtils.getString(object1), list1, str3);
                    if (list2 != null && list2.size() > 0) {
                        ArrayList<String> arrayList2 = new ArrayList<>();
                        for (DictModel dictModel : list2) {
                            if (dictModel.getValue().contains(str4)) {
                                String str6 = dictModel.getValue().replace(str4, str5);
                                arrayList2.add(dictModel.getText() + str4 + str6);
                                continue;
                            }
                            arrayList2.add(dictModel.getText() + str4 + dictModel.getValue());
                        }
                        excelExportEntity.setReplace(arrayList2.<String>toArray(new String[arrayList2.size()]));
                    }
                    Object object2 = ((Map) list.get(b1)).get("replace_val");
                    if (oConvertUtils.isNotEmpty(object2))
                        excelExportEntity.setReplace(object2.toString().split(","));
                    if (oConvertUtils.isNotEmpty(((Map) list.get(b1)).get("group_title"))) {
                        String str6 = ((Map) list.get(b1)).get("group_title").toString();
                        List<String> list3 = new ArrayList<>();
                        if (hashMap2.containsKey(str6)) {
                            list3 = (List) hashMap2.get(str6);
                            list3.add(str3);
                        } else {
                            ExcelExportEntity excelExportEntity1 = new ExcelExportEntity(str6, str6, true);
                            arrayList1.add(excelExportEntity1);
                            list3.add(str3);
                        }
                        hashMap2.put(str6, list3);
                        excelExportEntity.setColspan(true);
                    }
                    if (oConvertUtils.isNotEmpty(str))
                        if (oConvertUtils.isEmpty(object1) && ("Integer".equals(str) || "Long".equals(str)))
                            excelExportEntity.setType(4);
                    arrayList1.add(excelExportEntity);
                }
                if ("1".equals(oConvertUtils.getString(((Map) list.get(b1)).get("is_total"))))
                    arrayList.add(((Map) list.get(b1)).get("field_name").toString());
            }
            for (Map.Entry<Object, Object> entry : hashMap2.entrySet()) {
                String str = (String) entry.getKey();
                List list2 = (List) entry.getValue();
                for (ExcelExportEntity excelExportEntity : arrayList1) {
                    if (str.equals(excelExportEntity.getName()) && excelExportEntity.isColspan())
                        excelExportEntity.setSubColumnList(list2);
                }
            }
            if (arrayList != null && arrayList.size() > 0) {
                for (String str : arrayList) {
                    BigDecimal bigDecimal = new BigDecimal(0.0D);
                    for (Map map1 : list1) {
                        String str3 = map1.get(str).toString();
                        if (str3.matches("\\d+(.\\d+)?")) {
                            BigDecimal bigDecimal1 = new BigDecimal(str3);
                            bigDecimal = bigDecimal.add(bigDecimal1);
                        }
                    }
                    hashMap1.put(str, bigDecimal);
                }
                list1.add(hashMap1);
            }
            paramHttpServletResponse.setContentType("application/vnd.ms-excel");
            ServletOutputStream servletOutputStream = null;
            try {
                String str = BrowserUtils.checkBrowse(paramHttpServletRequest);
                if ("MSIE".equalsIgnoreCase(str.substring(0, 4))) {
                    paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str1, "UTF-8") + ".xls");
                } else {
                    String str3 = new String(str1.getBytes("UTF-8"), "ISO8859-1");
                    paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + str3 + ".xls");
                }
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, str2), arrayList1, list1);
                servletOutputStream = paramHttpServletResponse.getOutputStream();
                workbook.write((OutputStream) servletOutputStream);
            } catch (Exception exception) {
                try {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                } catch (Exception exception1) {
                }
            } finally {
                try {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                } catch (Exception exception) {
                }
            }
        } else {
            throw new JeecgBootException("参数错误");
        }
    }

    @GetMapping({"/getRpColumns/{code}"})
    public Result<?> d(@PathVariable("code") String paramString) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgreportHead::getCode, paramString);
        OnlCgreportHead onlCgreportHead = (OnlCgreportHead) this.onlCgreportHeadService.getOne((Wrapper) lambdaQueryWrapper);
        if (onlCgreportHead == null)
            return Result.error("实体不存在");
        Map<String, String> map = this.onlCgreportHeadService.queryColumnInfo(onlCgreportHead.getId(), true);
        map.put("cgRpConfigId", onlCgreportHead.getId());
        map.put("cgRpConfigName", onlCgreportHead.getName());
        return Result.ok(map);
    }

    @PostMapping({"/testConnection"})
    public Result a(@RequestBody DynamicDataSourceModel paramDynamicDataSourceModel) {
        Connection connection = null;
        try {
            JdbcSecurityUtil.validate(paramDynamicDataSourceModel.getDbUrl());
            Class.forName(paramDynamicDataSourceModel.getDbDriver());
            connection = DriverManager.getConnection(paramDynamicDataSourceModel.getDbUrl(), paramDynamicDataSourceModel.getDbUsername(), paramDynamicDataSourceModel.getDbPassword());
            if (connection != null)
                return Result.ok("数据库连接成功");
            return Result.ok("数据库连接失败：错误未知");
        } catch (ClassNotFoundException classNotFoundException) {
            a.error(classNotFoundException.toString());
            return Result.error("数据库连接失败：驱动类不存在");
        } catch (Exception exception) {
            a.error(exception.toString());
            return Result.error("数据库连接失败：" + exception.getMessage());
        } finally {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
            } catch (SQLException sQLException) {
                a.error(sQLException.toString());
            }
        }
    }

    @GetMapping({"/getReportDictList"})
    public Result<?> a(@RequestParam("sql") String paramString1, @RequestParam(name = "keyword", required = false) String paramString2) {
        if (!this.onlReportQueryBlackListHandler.isPass(paramString1))
            return Result.error(this.onlReportQueryBlackListHandler.getError());
        List list = this.onlCgreportHeadService.queryDictSelectData(paramString1, paramString2);
        return Result.ok(list);
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\a\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
