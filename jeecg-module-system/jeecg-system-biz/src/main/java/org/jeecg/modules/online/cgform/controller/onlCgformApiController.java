package org.jeecg.modules.online.cgform.controller;


import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.AutoLowApp;
import org.jeecg.common.aspect.annotation.OnlineAuth;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.enums.LowAppAopEnum;
import org.jeecg.common.constant.enums.ModuleType;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.*;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.a1.aEntity;
import org.jeecg.modules.online.cgform.converter.b2;
import org.jeecg.modules.online.cgform.dConstants.aConstant;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.dConstants.d;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.Col;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.model.dModel;
import org.jeecg.modules.online.cgform.service.*;
import org.jeecg.modules.online.config.dUtil.eDbTableHandle;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.handler.inter.IExcelDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.sql.DataSource;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController("onlCgformApiController")
@RequestMapping({"/online/cgform/api"})
public class onlCgformApiController {
    private static final Logger a = LoggerFactory.getLogger(onlCgformApiController.class);

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    IOnlineJoinQueryService onlineJoinQueryService;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlCgformSqlService onlCgformSqlService;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlineService onlineService;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Value("${jeecg.uploadType}")
    private String uploadType;

    @Autowired
    private RedisUtil redisUtil;

    @AutoLowApp(action = LowAppAopEnum.ADD, bizType = "cgform")
    @PostMapping({"/addAll"})
    public Result<?> a(@RequestBody org.jeecg.modules.online.cgform.model.a parama) {
        try {

            String str = parama.getHead().getTableName();

            if (eDbTableHandle.a(str).booleanValue())
                return Result.error("数据库表[" + str + "]已存在,请从数据库导入表单");

            if (parama.getHead().getTableType().intValue() == 3) {

                if (oConvertUtils.isEmpty(parama.getHead().getRelationType()))
                    return Result.error("附表必须选择映射关系！");

                if (oConvertUtils.isEmpty(parama.getHead().getTabOrderNum()))
                    return Result.error("附表必须填写排序序号！");
            }

            return this.onlCgformHeadService.addAll(parama);

        } catch (Exception exception) {

            a.error("OnlCgformApiController.addAll()发生异常：" + exception.getMessage(), exception);

            return Result.error("操作失败");
        }
    }

    @PutMapping({"/editAll"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@RequestBody org.jeecg.modules.online.cgform.model.a parama) {
        try {

            if (parama.getHead().getTableType().intValue() == 3) {

                if (oConvertUtils.isEmpty(parama.getHead().getRelationType()))
                    return Result.error("附表必须选择映射关系！");

                if (oConvertUtils.isEmpty(parama.getHead().getTabOrderNum()))
                    return Result.error("附表必须填写排序序号！");
            }

            return this.onlCgformHeadService.editAll(parama);

        } catch (Exception exception) {

            a.error("OnlCgformApiController.editAll()发生异常：" + exception.getMessage(), exception);

            return Result.error("操作失败");
        }
    }

    @AutoLog(operateType = 1, value = "online列表加载", module = ModuleType.ONLINE)
    @OnlineAuth("getColumns")
    @GetMapping({"/getColumns/{code}"})
    public Result<Col> a(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        Result<Col> result = new Result();
        OnlCgformHead onlCgformHead = null;
        try {
            onlCgformHead = this.onlCgformHeadService.getTable(paramString);
        } catch (AException a1) {
            result.error500("实体不存在");
            return result;
        }

        String str = paramHttpServletRequest.getParameter("linkTableSelectFields");

        if (oConvertUtils.isNotEmpty(str))
            onlCgformHead.setSelectFieldString(str);

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Col b = this.onlineService.queryOnlineConfig(onlCgformHead, loginUser.getUsername());

        b.setIsDesForm(onlCgformHead.getIsDesForm());

        b.setDesFormCode(onlCgformHead.getDesFormCode());

        result.setResult(b);

        result.setOnlTable(onlCgformHead.getTableName());

        return result;
    }

    @PermissionData
    @OnlineAuth("getData")
    @GetMapping({"/getData/{code}"})
    public Result<Map<String, Object>> b(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        Result<Map<String, Object>> result = new Result();
        OnlCgformHead onlCgformHead = null;
        try {
            onlCgformHead = this.onlCgformHeadService.getTable(paramString);
        } catch (AException a1) {
            result.error500("实体不存在");
            return result;
        }
        if (oConvertUtils.isEmpty(onlCgformHead.getPhysicId()) && "N".equals(onlCgformHead.getIsDbSynch())) {
            result.error500("NO_DB_SYNC");
            return result;
        }

        String str = paramHttpServletRequest.getParameter("linkTableSelectFields");
        if (oConvertUtils.isNotEmpty(str))
            onlCgformHead.setSelectFieldString(str);
        Map<String, Object> map = null;
        try {
            Map map1 = bConstant.getId(paramHttpServletRequest);
            boolean bool = bConstant.getId(onlCgformHead);
            if (bool) {
                map = this.onlineJoinQueryService.pageList(onlCgformHead, map1);
            } else {
                map = this.onlCgformFieldService.queryAutolistPage(onlCgformHead, map1, null);
            }
            a(onlCgformHead, map);
            result.setResult(map);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("数据库查询失败，" + exception.getMessage());
        }
        result.setOnlTable(onlCgformHead.getTableName());
        return result;
    }

    @AutoLog(operateType = 1, value = "online表单加载", module = ModuleType.ONLINE)
    @OnlineAuth("getFormItem")
    @GetMapping({"/getFormItem/{code}"})
    public Result<?> c(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        OnlCgformHead onlCgformHead = null;
        try {
            onlCgformHead = this.onlCgformHeadService.getTable(paramString);
        } catch (AException a1) {
            return Result.error("表不存在");
        }
        Result<JSONObject> result = new Result<>();
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str = paramHttpServletRequest.getParameter("selectFields");
        if (oConvertUtils.isNotEmpty(str)) {
            List<String> list = Arrays.asList(str.split(","));
        }
        JSONObject jSONObject = this.onlineService.queryOnlineFormItem(onlCgformHead, loginUser.getUsername());
        result.setResult(bConstant.b(jSONObject));
        result.setOnlTable(onlCgformHead.getTableName());
        return result;
    }

    @AutoLog(operateType = 1, value = "online根据表名加载表单", module = ModuleType.ONLINE)
    @GetMapping({"/getFormItemBytbname/{table}"})
    public Result<?> a(@PathVariable("table") String paramString1, @RequestParam(name = "taskId", required = false) String paramString2) {
        Result<JSONObject> result = new Result();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, paramString1);
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
        if (onlCgformHead == null)
            Result.error("表不存在");
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        JSONObject jSONObject = this.onlineService.queryFlowOnlineFormItem(onlCgformHead, loginUser.getUsername(), paramString2);

        result.setResult(bConstant.b(jSONObject));

        result.setOnlTable(paramString1);

        return result;
    }

    @OnlineAuth("getEnhanceJs")
    @GetMapping({"/getEnhanceJs/{code}"})
    public Result<?> d(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {

        String str = this.onlineService.queryEnahcneJsString(paramString, "form");

        return Result.ok(str);
    }

    @AutoLog(operateType = 1, value = "online表单数据查询")
    @GetMapping({"/form/{code}/{id}"})
    public Result<?> b(@PathVariable("code") String paramString1, @PathVariable("id") String paramString2) {
        try {

            SqlInjectionUtil.filterContent(paramString2, "'");

            Map map = this.onlCgformHeadService.queryManyFormData(paramString1, paramString2);

            return Result.ok(bConstant.getId(map));

        } catch (Exception exception) {

            a.error("Online表单查询异常：" + exception.getMessage(), exception);

            return Result.error("查询失败，" + exception.getMessage());
        }
    }

    @AutoLog(operateType = 1, value = "online表单数据查询")
    @GetMapping({"/detail/{code}/{id}"})
    public Result<?> c(@PathVariable("code") String paramString1, @PathVariable("id") String paramString2) {
        try {
            SqlInjectionUtil.filterContent(paramString2, "'");
            Map<String, Object> map = this.onlCgformHeadService.queryManyFormData(paramString1, paramString2);
            ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
            arrayList.add(bConstant.getId(map));
            OnlCgformHead onlCgformHead = this.onlCgformHeadService.getTable(paramString1);
            this.onlCgformFieldService.handleLinkTableDictData(onlCgformHead.getId(), arrayList);
            return Result.ok(arrayList.get(0));
        } catch (Exception exception) {
            a.error("Online表单查询异常：" + exception.getMessage(), exception);
            return Result.error("查询失败，" + exception.getMessage());
        }
    }

    @GetMapping({"/subform/{table}/{mainId}"})
    public Result<?> d(@PathVariable("table") String paramString1, @PathVariable("mainId") String paramString2) {
        try {
            SqlInjectionUtil.filterContent(paramString2, "'");
            Map map = this.onlCgformHeadService.querySubFormData(paramString1, paramString2);
            return Result.ok(bConstant.getId(map));
        } catch (Exception exception) {
            a.error("Online表单查询异常：" + exception.getMessage(), exception);
            return Result.error("查询失败，" + exception.getMessage());
        }
    }

    @GetMapping({"/subform/list/{table}/{mainId}"})
    public Result<?> e(@PathVariable("table") String paramString1, @PathVariable("mainId") String paramString2) {
        try {
            SqlInjectionUtil.filterContent(paramString2, "'");
            return Result.ok(this.onlCgformHeadService.queryManySubFormData(paramString1, paramString2));
        } catch (Exception exception) {
            a.error("Online表单查询异常：" + exception.getMessage(), exception);
            return Result.error("查询失败，" + exception.getMessage());
        }
    }

    @AutoLog(operateType = 1, value = "online根据表名查询表单数据", module = ModuleType.ONLINE)
    @GetMapping({"/form/table_name/{tableName}/{dataId}"})
    public Result<?> f(@PathVariable("tableName") String paramString1, @PathVariable("dataId") String paramString2) {
        try {
            LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformHead.class)
                    .eq(OnlCgformHead::getTableName, paramString1);

            OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
            if (onlCgformHead == null)
                throw new Exception("OnlCgform tableName: " + paramString1 + " 不存在！");
            SqlInjectionUtil.filterContent(paramString2, "'");
            Result<?> result = b(onlCgformHead.getId(), paramString2);
            result.setOnlTable(paramString1);
            return result;
        } catch (Exception exception) {
            a.error("Online表单查询异常，" + exception.getMessage(), exception);
            return Result.error("查询失败，" + exception.getMessage());
        }
    }

    @AutoLog(operateType = 2, value = "online新增数据", module = ModuleType.ONLINE)
    @OnlineAuth("form")
    @PostMapping({"/form/{code}"})
    @CacheEvict(value = {"sys:cache:online:linkTable"}, allEntries = true)
    public Result<String> a(@PathVariable("code") String paramString, @RequestBody JSONObject paramJSONObject, HttpServletRequest paramHttpServletRequest) {

        Result<String> result = new Result();
        try {

            String str1 = bConstant.getId();

            paramJSONObject.put("id", str1);

            String str2 = TokenUtils.getTokenByRequest(paramHttpServletRequest);

            String str3 = this.onlCgformHeadService.saveManyFormData(paramString, paramJSONObject, str2);

            result.setSuccess(true);

            result.setResult(str1);

            result.setOnlTable(str3);

            result.setMessage("添加成功!");

        } catch (Exception exception) {

            a.error("OnlCgformApiController.formAdd()发生异常：", exception);

            result.setSuccess(false);

            result.setMessage("保存失败，" + bConstant.getId(exception));
        }

        return result;
    }

    @AutoLog(operateType = 3, value = "online修改数据", module = ModuleType.ONLINE)
    @OnlineAuth("form")
    @PutMapping({"/form/{code}"})
    @CacheEvict(value = {"sys:cache:online:linkTable"}, allEntries = true)
    public Result<?> a(@PathVariable("code") String paramString, @RequestBody JSONObject paramJSONObject) {
        try {

            String str = this.onlCgformHeadService.editManyFormData(paramString, paramJSONObject);

            Result<?> result = Result.ok("修改成功！");

            result.setOnlTable(str);

            return result;

        } catch (Exception exception) {

            a.error("OnlCgformApiController.formEdit()发生异常：" + exception.getMessage(), exception);

            return Result.error("修改失败，" + bConstant.getId(exception));
        }
    }

    @AutoLog(operateType = 4, value = "online删除数据", module = ModuleType.ONLINE)
    @OnlineAuth("form")
    @DeleteMapping({"/form/{code}/{id}"})
    public Result<?> g(@PathVariable("code") String paramString1, @PathVariable("id") String paramString2) {

        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString1);

        if (onlCgformHead == null)
            return Result.error("实体不存在");
        try {

            String str = "";

            if ("Y".equals(onlCgformHead.getIsTree())) {

                paramString2 = this.onlCgformFieldService.queryTreeChildIds(onlCgformHead, paramString2);

                str = this.onlCgformFieldService.queryTreePids(onlCgformHead, paramString2);
            }

            if (paramString2.indexOf(",") > 0) {

                if (onlCgformHead.getTableType().intValue() == 2) {

                    this.onlCgformFieldService.deleteAutoListMainAndSub(onlCgformHead, paramString2);
                } else {

                    String str1 = onlCgformHead.getTableName();

                    this.onlCgformFieldService.deleteAutoListById(str1, paramString2);
                }

                if ("Y".equals(onlCgformHead.getIsTree())) {

                    String str1 = onlCgformHead.getTableName();

                    String str2 = onlCgformHead.getTreeIdField();

                    String[] arrayOfString = str.split(",");

                    for (String str3 : arrayOfString)

                        this.onlCgformFieldService.updateTreeNodeNoChild(str1, str2, str3);
                }
            } else {

                this.onlCgformHeadService.deleteOneTableInfo(paramString1, paramString2);
            }

        } catch (Exception exception) {

            a.error("OnlCgformApiController.formEdit()发生异常：" + exception.getMessage(), exception);

            return Result.error("删除失败," + exception.getMessage());
        }

        Result<?> result = Result.ok("删除成功!");

        result.setOnlTable(onlCgformHead.getTableName());

        return result;
    }

    @AutoLog(operateType = 4, value = "online删除数据", module = ModuleType.ONLINE)
    @DeleteMapping({"/formByCode/{code}/{id}"})
    public Result<?> h(@PathVariable("code") String paramString1, @PathVariable("id") String paramString2) {
        try {

            String str = this.onlCgformHeadService.deleteDataByCode(paramString1, paramString2);

            Result<?> result = Result.OK("删除成功!", str);

            result.setOnlTable(str);

            return result;

        } catch (JeecgBootException jeecgBootException) {

            return Result.error(jeecgBootException.getMessage());
        }
    }

    @OnlineAuth("getQueryInfo")
    @GetMapping({"/getQueryInfo/{code}"})
    public Result<?> a(@PathVariable("code") String paramString) {
        try {

            List list = this.onlCgformFieldService.getAutoListQueryInfo(paramString);

            return Result.ok(list);

        } catch (Exception exception) {

            a.error("OnlCgformApiController.getQueryInfo()发生异常：" + exception.getMessage(), exception);

            return Result.error("查询失败");
        }
    }

    @GetMapping({"/getQueryInfoVue3/{code}"})
    public Result<?> b(@PathVariable("code") String paramString) {
        try {

            JSONObject jSONObject = this.onlineService.getOnlineVue3QueryInfo(paramString);

            return Result.ok(jSONObject);

        } catch (Exception exception) {

            a.error("OnlCgformApiController.getQueryInfoVue3()发生异常：" + exception.getMessage(), exception);

            return Result.error("查询失败");
        }
    }

    @PostMapping({"/doDbSynch/{code}/{synMethod}"})
    public Result<?> i(@PathVariable("code") String paramString1, @PathVariable("synMethod") String paramString2) {
        try {

            long l = System.currentTimeMillis();

            this.onlCgformHeadService.doDbSynch(paramString1, paramString2);

        } catch (Exception exception) {

            a.error(exception.getMessage(), exception);

            return Result.error("同步数据库失败，" + bConstant.getId(exception));
        }

        return Result.ok("同步数据库成功!");
    }

    @OnlineAuth("exportXls")
    @PermissionData
    @GetMapping({"/exportXls/{code}"})
    public void a(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {

        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString);

        if (onlCgformHead == null)
            return;

        String str1 = onlCgformHead.getTableTxt();

        String str2 = paramHttpServletRequest.getParameter("paramsStr");

        Map map = new HashMap<>(5);

        Object object = null;

        if (oConvertUtils.isNotEmpty(str2))
            map = (Map) JSONObject.parseObject(str2, Map.class);

        XSSFWorkbook xSSFWorkbook = this.onlineJoinQueryService.handleOnlineExport(onlCgformHead, map);

        ServletOutputStream servletOutputStream = null;
        try {

            paramHttpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            String str3 = BrowserUtils.checkBrowse(paramHttpServletRequest);

            String str4 = onlCgformHead.getTableTxt() + "-v" + onlCgformHead.getTableVersion();

            if ("MSIE".equalsIgnoreCase(str3.substring(0, 4))) {

                paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str4, "UTF-8") + ".xlsx");
            } else {

                String str = new String(str4.getBytes("UTF-8"), "ISO8859-1");

                paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + str + ".xlsx");
            }

            servletOutputStream = paramHttpServletResponse.getOutputStream();

            xSSFWorkbook.write((OutputStream) servletOutputStream);

            paramHttpServletResponse.flushBuffer();

        } catch (Exception exception) {

            a.error("--通过流的方式获取文件异常--" + exception.getMessage(), exception);
        } finally {

            if (servletOutputStream != null)
                try {

                    servletOutputStream.close();

                } catch (IOException iOException) {

                    a.error(iOException.getMessage(), iOException);
                }
        }
    }

    @OnlineAuth("exportXlsOld")
    @PermissionData
    @GetMapping({"/exportXlsOld/{code}"})
    public void b(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(paramString);

        if (onlCgformHead == null)
            return;
        String str1 = onlCgformHead.getTableTxt();
        String str2 = paramHttpServletRequest.getParameter("paramsStr");
        Map<String, Object> map = new HashMap<>(5);
        Object object = null;
        if (oConvertUtils.isNotEmpty(str2))
            map = (Map) JSONObject.parseObject(str2, Map.class);
        map.put("pageSize", -521);
        boolean bool = bConstant.getId(onlCgformHead);
        Map map1 = null;
        if (bool) {
            map1 = this.onlineJoinQueryService.pageList(onlCgformHead, map, true);
        } else {
            map1 = this.onlCgformFieldService.queryAutolistPage(onlCgformHead, map, null);
        }
        List list1 = (List) map1.get("fieldList");
        List list2 = (List) map1.get("records");
        List list3 = new ArrayList<>();
        String str3 = (map.get("selections") == null) ? null : map.get("selections").toString();
        if (oConvertUtils.isNotEmpty(str3)) {
            List list = bConstant.h(str3);
            list3 = (List) list2.stream().filter(paramMap -> paramList.contains(paramMap.get("id")))
                    .collect(Collectors.toList());
        } else {
            if (list2 == null)
                list2 = new ArrayList<>();
            list3.addAll(list2);
        }
        bConstant.getId(1, list3, list1);
        try {
            this.onlCgformHeadService.executeEnhanceExport(onlCgformHead, list3);
        } catch (BusinessException businessException) {
            a.error("导出java增强处理出错", businessException.getMessage());
        }
        List list4 = bConstant.getId(list1, "id", this.upLoadPath);
        if (onlCgformHead.getTableType() == 2)
            if (oConvertUtils.isEmpty(map.get("exportSingleOnly"))) {
                String str = onlCgformHead.getSubTableStr();
                if (oConvertUtils.isNotEmpty(str)) {
                    String[] arrayOfString = str.split(",");
                    for (String str4 : arrayOfString)
                        this.onlineJoinQueryService.addAllSubTableDate(str4, map, list3, list4, false);
                }
            }

        ExportParams exportParams = new ExportParams(null, str1);
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, list4, list3);
        ServletOutputStream servletOutputStream = null;
        try {
            paramHttpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String str4 = BrowserUtils.checkBrowse(paramHttpServletRequest);
            String str5 = onlCgformHead.getTableTxt() + "-v" + onlCgformHead.getTableVersion();
            if ("MSIE".equalsIgnoreCase(str4.substring(0, 4))) {
                paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(str5, "UTF-8") + ".xlsx");
            } else {
                String str = new String(str5.getBytes("UTF-8"), "ISO8859-1");
                paramHttpServletResponse.setHeader("content-disposition", "attachment;filename=" + str + ".xlsx");
            }
            servletOutputStream = paramHttpServletResponse.getOutputStream();
            workbook.write(servletOutputStream);
            paramHttpServletResponse.flushBuffer();
        } catch (Exception exception) {
            a.error("--通过流的方式获取文件异常--" + exception.getMessage(), exception);
        } finally {
            if (servletOutputStream != null)
                try {
                    servletOutputStream.close();
                } catch (IOException iOException) {
                    a.error(iOException.getMessage(), iOException);
                }
        }
    }

    @OnlineAuth("importXls")
    @PostMapping({"/importXls/{code}"})
    public Result<?> c(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {

        long l = System.currentTimeMillis();

        Result<?> result = new Result();

        String str1 = "";

        String str2 = paramHttpServletRequest.getParameter("validateStatus");

        StringBuffer stringBuffer = new StringBuffer();
        try {

            OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString);

            if (onlCgformHead == null)
                return Result.error("数据库不存在该表记录");
            LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformField.class)
                    .eq(OnlCgformField::getCgformHeadId, paramString);
            List<OnlCgformField> list1 = this.onlCgformFieldService.list(lambdaQueryWrapper);
            String str3 = paramHttpServletRequest.getParameter("isSingleTableImport");
            List list2 = bConstant.e(list1);

            if (oConvertUtils.isEmpty(str3) && onlCgformHead.getTableType() == 2 && oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr()))
                for (String str : onlCgformHead.getSubTableStr().split(",")) {

                    OnlCgformHead onlCgformHead1 = this.onlCgformHeadService.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead1 != null) {
                        List<OnlCgformField> list3 = this.onlCgformFieldService.list(Wrappers.lambdaQuery(OnlCgformField.class)
                                .eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId()));
                        List list4 = bConstant.b(list3, onlCgformHead1.getTableTxt());
                        if (list4.size() > 0)
                            list2.addAll(list4);
                    }
                }

            JSONObject jSONObject = null;
            String str4 = paramHttpServletRequest.getParameter("foreignKeys");
            if (oConvertUtils.isNotEmpty(str4))
                jSONObject = JSONObject.parseObject(str4);
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) paramHttpServletRequest;
            Map<String, MultipartFile> map = multipartHttpServletRequest.getFileMap();
            DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
            String str5 = eDbTableHandle.a(dataSource);

            for (Map.Entry entry : map.entrySet()) {
                MultipartFile multipartFile = (MultipartFile) entry.getValue();
                ImportParams importParams = new ImportParams();
                importParams.setImageList(list2);
                importParams.setDataHanlder(new aConstant(list1, this.upLoadPath, this.uploadType));
                List list = ExcelImportUtil.importExcel(multipartFile.getInputStream(), Map.class, importParams);
                if (list == null) {
                    str1 = "识别模版数据错误";
                    a.error(str1);
                    continue;
                }
                if (org.jeecg.modules.online.cgform.enums.a.a.equals(onlCgformHead.getTableType()) && onlCgformHead.getRelationType().intValue() == 1 && list.size() > 1)
                    return Result.error("一对一的表只能导入一条数据!");
                String str = "";
                ArrayList<HashMap<Object, Object>> arrayList = new ArrayList<>();
                for (Map<String, Object> map1 : (Iterable<Map<String, Object>>) list) {
                    Object object;
                    boolean bool = false;
                    Set set = map1.keySet();
                    HashMap<Object, Object> hashMap = new HashMap<>(5);
                    for (String str6 : set) {

                        if (str6.indexOf("$subTable$") == -1) {

                            if (str6.indexOf("$mainTable$") != -1 && oConvertUtils.isNotEmpty(map1.get(str6).toString())) {

                                bool = true;

                                object = a(onlCgformHead, dataSource, str5);
                            }

                            hashMap.put(str6.replace("$mainTable$", ""), map1.get(str6));
                        }
                    }

                    if ("Y".equals(onlCgformHead.getIsTree())) {

                        if (oConvertUtils.isEmpty(hashMap.get(onlCgformHead.getTreeParentIdField())))
                            hashMap.put(onlCgformHead.getTreeParentIdField(), "0");

                        if (oConvertUtils.isEmpty(hashMap.get(onlCgformHead.getTreeIdField())))
                            hashMap.put(onlCgformHead.getTreeIdField(), "0");
                    }

                    if (bool) {

                        hashMap.put("id", object);

                        arrayList.add(hashMap);

                        object = hashMap.get("id");
                    }

                    if (jSONObject != null)
                        for (String str6 : jSONObject.keySet()) {

                            System.out.println(str6 + "=" + jSONObject.getString(str6));

                            hashMap.put(str6, jSONObject.getString(str6));
                        }

                    map1.put("$mainTable$id", object);
                }

                if (arrayList == null || arrayList.size() == 0) {

                    result.setSuccess(false);

                    result.setMessage("导入失败，匹配的数据条数为零!");

                    return result;
                }

                if ("1".equals(str2)) {

                    Map map1 = this.onlCgformSqlService.saveOnlineImportDataWithValidate(onlCgformHead, list1, arrayList);

                    String str6 = (String) map1.get("error");

                    str1 = (String) map1.get("tip");

                    if (str6 != null && str6.length() > 0)

                        stringBuffer.append(onlCgformHead.getTableTxt() + "导入校验," + str1 + ",详情如下:\r\n" + str6);
                } else {

                    this.onlCgformSqlService.saveBatchOnlineTable(onlCgformHead, list1, arrayList);
                }

                if (oConvertUtils.isEmpty(str3) && onlCgformHead.getTableType().intValue() == 2 && oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr()))
                    for (String str6 : onlCgformHead.getSubTableStr().split(",")) {

                        OnlCgformHead onlCgformHead1 = (OnlCgformHead) this.onlCgformHeadService.getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str6));

                        if (onlCgformHead1 != null) {

                            LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();

                            lambdaQueryWrapper1.eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId());

                            List list3 = this.onlCgformFieldService.list((Wrapper) lambdaQueryWrapper1);

                            ArrayList<HashMap<Object, Object>> arrayList1 = new ArrayList<>();

                            String str7 = onlCgformHead1.getTableTxt();
                            for (Map map1 : list) {

                                boolean bool = false;

                                HashMap<Object, Object> hashMap = new HashMap<>();

                                for (OnlCgformField onlCgformField : list3) {

                                    String str8 = onlCgformField.getMainTable();

                                    String str9 = onlCgformField.getMainField();

                                    boolean bool1 = (onlCgformHead.getTableName().equals(str8) && oConvertUtils.isNotEmpty(str9)) ? true : false;

                                    String str10 = str7 + "_" + onlCgformField.getDbFieldTxt();

                                    if (bool1)

                                        hashMap.put(onlCgformField.getDbFieldName(), map1.get("$mainTable$" + str9));

                                    Object object = map1.get("$subTable$" + str10);

                                    if (null != object && oConvertUtils.isNotEmpty(object.toString())) {

                                        bool = true;

                                        hashMap.put(onlCgformField.getDbFieldName(), object);
                                    }
                                }

                                if (bool) {

                                    hashMap.put("id", a(onlCgformHead1, dataSource, str5));

                                    arrayList1.add(hashMap);
                                }
                            }

                            if (arrayList1.size() > 0)
                                if ("1".equals(str2)) {

                                    Map map1 = this.onlCgformSqlService.saveOnlineImportDataWithValidate(onlCgformHead1, list3, arrayList1);

                                    String str8 = (String) map1.get("error");

                                    String str9 = (String) map1.get("tip");

                                    if (str8 != null && str8.length() > 0)

                                        stringBuffer.append(onlCgformHead1.getTableTxt() + "导入校验," + str9 + ",详情如下:\r\n" + str8);
                                } else {

                                    this.onlCgformSqlService.saveBatchOnlineTable(onlCgformHead1, list3, arrayList1);
                                }
                        }
                    }
            }

            result.setSuccess(true);

            if ("1".equals(str2) && stringBuffer.length() > 0) {

                String str = bConstant.getId(this.upLoadPath, onlCgformHead.getTableTxt(), stringBuffer);

                result.setResult(str);

                result.setMessage(str1);

                result.setCode(Integer.valueOf(201));
            } else {

                result.setMessage("导入成功!");
            }

        } catch (Exception exception) {

            result.setSuccess(false);

            result.setMessage(exception.getMessage());

            a.error(exception.getMessage(), exception);
        }

        return result;
    }

    @PostMapping({"/doButton"})
    public Result<?> a(@RequestBody JSONObject paramJSONObject) {
        String str1 = paramJSONObject.getString("formId");
        String str2 = paramJSONObject.getString("dataId");
        String str3 = paramJSONObject.getString("buttonCode");
        JSONObject jSONObject = paramJSONObject.getJSONObject("uiFormData");
        try {
            this.onlCgformHeadService.executeCustomerButton(str3, str1, str2);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("执行失败," + exception.getMessage());
        }
        return Result.ok("执行成功!");
    }

    public Object a(OnlCgformHead paramOnlCgformHead, DataSource paramDataSource, String paramString) throws SQLException, AException {

        String str1 = null;

        String str2 = paramOnlCgformHead.getIdType();

        String str3 = paramOnlCgformHead.getIdSequence();

        if (oConvertUtils.isNotEmpty(str2) && "UUID".equalsIgnoreCase(str2)) {

            str1 = bConstant.getId();

        } else if (oConvertUtils.isNotEmpty(str2) && "NATIVE".equalsIgnoreCase(str2)) {

            if (oConvertUtils.isNotEmpty(paramString) && "oracle".equalsIgnoreCase(paramString)) {

                OracleSequenceMaxValueIncrementer oracleSequenceMaxValueIncrementer = new OracleSequenceMaxValueIncrementer(paramDataSource, "HIBERNATE_SEQUENCE");
                try {

                    Long long_ = Long.valueOf(oracleSequenceMaxValueIncrementer.nextLongValue());

                } catch (Exception exception) {

                    a.error(exception.getMessage(), exception);
                }

            } else if (oConvertUtils.isNotEmpty(paramString) && "postgres".equalsIgnoreCase(paramString)) {

//                PostgreSQLSequenceMaxValueIncrementer postgreSQLSequenceMaxValueIncrementer = new PostgreSQLSequenceMaxValueIncrementer(paramDataSource, "HIBERNATE_SEQUENCE");
//                try {
//
//                    Long long_ = Long.valueOf(postgreSQLSequenceMaxValueIncrementer.nextLongValue());
//
//                } catch (Exception exception) {
//
//                    a.error(exception.getMessage(), exception);
//                }
            } else {

                str1 = null;
            }

        } else if (oConvertUtils.isNotEmpty(str2) && "SEQUENCE".equalsIgnoreCase(str2)) {

            if (oConvertUtils.isNotEmpty(paramString) && "oracle".equalsIgnoreCase(paramString)) {

                OracleSequenceMaxValueIncrementer oracleSequenceMaxValueIncrementer = new OracleSequenceMaxValueIncrementer(paramDataSource, str3);
                try {

                    Long long_ = Long.valueOf(oracleSequenceMaxValueIncrementer.nextLongValue());

                } catch (Exception exception) {

                    a.error(exception.getMessage(), exception);
                }

            } else if (oConvertUtils.isNotEmpty(paramString) && "postgres".equalsIgnoreCase(paramString)) {

//                PostgreSQLSequenceMaxValueIncrementer postgreSQLSequenceMaxValueIncrementer = new PostgreSQLSequenceMaxValueIncrementer(paramDataSource, str3);
//                try {
//
//                    Long long_ = Long.valueOf(postgreSQLSequenceMaxValueIncrementer.nextLongValue());
//
//                } catch (Exception exception) {
//
//                    a.error(exception.getMessage(), exception);
//                }
            } else {

                str1 = null;
            }
        } else {

            str1 = bConstant.getId();
        }

        return str1;
    }

    private void a(Map<String, String> paramMap, List<OnlCgformField> paramList) {

        for (OnlCgformField onlCgformField : paramList) {

            String str1 = onlCgformField.getDictTable();

            String str2 = onlCgformField.getDictField();

            String str3 = onlCgformField.getDictText();

            if (oConvertUtils.isEmpty(str1) && oConvertUtils.isEmpty(str2))
                continue;

            if (!"popup".equals(onlCgformField.getFieldShowType())) {
                List list;

                String str = String.valueOf(paramMap.get(onlCgformField.getDbFieldName()));

                if (oConvertUtils.isEmpty(str1)) {

                    list = this.sysBaseAPI.queryDictItemsByCode(str2);
                } else {

                    list = this.sysBaseAPI.queryTableDictItemsByCode(str1, str3, str2);
                }

                for (DictModel dictModel : list) {

                    if (str.equals(dictModel.getText()))
                        paramMap.put(onlCgformField.getDbFieldName(), dictModel.getValue());
                }
            }
        }
    }

    @GetMapping({"/checkOnlyTable"})
    public Result<?> j(@RequestParam("tbname") String paramString1, @RequestParam("id") String paramString2) {

        if (oConvertUtils.isEmpty(paramString2)) {

            if (eDbTableHandle.a(paramString1).booleanValue())
                return Result.ok(Integer.valueOf(-1));

            OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, paramString1));

            if (oConvertUtils.isNotEmpty(onlCgformHead))
                return Result.ok(Integer.valueOf(-1));
        } else {

            OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString2);

            if (!paramString1.equals(onlCgformHead.getTableName()) &&
                    eDbTableHandle.a(paramString1).booleanValue())
                return Result.ok(Integer.valueOf(-1));
        }

        return Result.ok(Integer.valueOf(1));
    }

    @PostMapping({"/codeGenerate"})
    public Result<?> b(@RequestBody JSONObject paramJSONObject) {
        dModel d = JSONObject.parseObject(paramJSONObject.toJSONString(), dModel.class);
        List list = null;
        try {
            if ("1".equals(d.getJformType())) {
                list = this.onlCgformHeadService.generateCode(d);
            } else {
                list = this.onlCgformHeadService.generateOneToMany(d);
            }
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            String str1 = loginUser.getUsername() + d.getTableName() + oConvertUtils.randomGen(16);
            Result<?> result = Result.ok(list);
            String str2 = d.getProjectPath().replaceAll("\\\\", "/");
            this.redisUtil.set(str1, str2, 1800L);
            result.setMessage(str1);
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            return Result.error(exception.getMessage());
        }
    }

    @GetMapping({"/codeView"})
    public void a(@RequestParam(name = "path") String paramString1, @RequestParam(name = "pathKey") String paramString2, HttpServletResponse paramHttpServletResponse) {
        String str1 = "";
        try {
            str1 = URLDecoder.decode(paramString1, "UTF-8");
            if (str1.indexOf("src/main/java") == -1 && str1.indexOf("src%5Cmain%5Cjava") == -1 && str1.indexOf("src\\main\\java") == -1) {

                a.error(" path 不合法！！！");
                return;
            }

        } catch (UnsupportedEncodingException unsupportedEncodingException) {

            a.error(" path 不合法！！！", unsupportedEncodingException.getMessage());
        }

        Object object = this.redisUtil.get(paramString2);

        if (object == null) {

            String str = "路径失效，请重新操作!";

            a.error(str);

            JwtUtil.responseError((ServletResponse) paramHttpServletResponse, Integer.valueOf(500), str);
            return;
        }

        String str2 = object.toString();

        String str3 = str1.replaceAll("\\\\", "/");

        if (str3.indexOf(str2) < 0) {

            String str = "非法的请求路径，请重新操作!";

            a.error(str);

            JwtUtil.responseError((ServletResponse) paramHttpServletResponse, Integer.valueOf(500), str);
            return;
        }

        String str4 = str1.substring(str1.lastIndexOf("/") + 1);

        File file = new File(str1);

        if (file.exists()) {

            paramHttpServletResponse.setContentType("application/force-download");

            paramHttpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + str4);

            byte[] arrayOfByte = new byte[1024];

            FileInputStream fileInputStream = null;

            BufferedInputStream bufferedInputStream = null;
            try {

                fileInputStream = new FileInputStream(file);

                bufferedInputStream = new BufferedInputStream(fileInputStream);

                ServletOutputStream servletOutputStream = paramHttpServletResponse.getOutputStream();

                int i = bufferedInputStream.read(arrayOfByte);

                while (i != -1) {

                    servletOutputStream.write(arrayOfByte, 0, i);

                    i = bufferedInputStream.read(arrayOfByte);
                }

            } catch (Exception exception) {

                exception.printStackTrace();
            } finally {

                if (bufferedInputStream != null)
                    try {

                        bufferedInputStream.close();

                    } catch (IOException iOException) {

                        iOException.printStackTrace();
                    }

                if (fileInputStream != null)
                    try {

                        fileInputStream.close();

                    } catch (IOException iOException) {

                        iOException.printStackTrace();
                    }
            }
        }
    }

    @PostMapping({"/downGenerateCode"})
    public void a(@RequestBody JSONObject paramJSONObject, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {

        String str1 = paramJSONObject.getString("fileList");

        String str2 = paramJSONObject.getString("pathKey");
        try {

            str1 = URLDecoder.decode(str1, "UTF-8");

        } catch (UnsupportedEncodingException unsupportedEncodingException) {

            a.error(" fileList 不合法！！！", unsupportedEncodingException.getMessage());
        }

        ArrayList<String> arrayList = new ArrayList<>();

        for (String str : str1.split(","))

            arrayList.add(str);

        List list = (List) arrayList.stream().filter(paramString -> (paramString.indexOf("src/main/java") == -1 && paramString.indexOf("src%5Cmain%5Cjava") == -1 && paramString.indexOf("src\\main\\java") == -1)).collect(Collectors.toList());

        if (arrayList == null || (list != null && list.size() > 0)) {

            a.error(" fileList 不合法！！！", arrayList);
            return;
        }

        Object object = this.redisUtil.get(str2);

        if (object == null) {

            String str = "路径失效，请重新操作!";

            a.error(str);

            JwtUtil.responseError((ServletResponse) paramHttpServletResponse, Integer.valueOf(500), str);
            return;
        }

        String str3 = object.toString();

        for (String str6 : arrayList) {

            String str7 = str6.replaceAll("\\\\", "/");

            if (str7.indexOf(str3) < 0) {

                String str = "非法的请求路径，请重新操作!";

                a.error(str);

                JwtUtil.responseError((ServletResponse) paramHttpServletResponse, Integer.valueOf(500), str);
                return;
            }
        }

        String str4 = "生成代码_" + System.currentTimeMillis() + ".zip";
        try {

            str4 = URLEncoder.encode(str4, "UTF-8");

        } catch (UnsupportedEncodingException unsupportedEncodingException) {
        }

        String str5 = "/opt/temp/codegenerate/" + str4;

        File file = d.a(arrayList, str5);

        if (file.exists()) {

            paramHttpServletResponse.setContentType("application/force-download");

            paramHttpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + str4);

            byte[] arrayOfByte = new byte[1024];

            FileInputStream fileInputStream = null;

            BufferedInputStream bufferedInputStream = null;
            try {

                fileInputStream = new FileInputStream(file);

                bufferedInputStream = new BufferedInputStream(fileInputStream);

                ServletOutputStream servletOutputStream = paramHttpServletResponse.getOutputStream();

                int i = bufferedInputStream.read(arrayOfByte);

                while (i != -1) {

                    servletOutputStream.write(arrayOfByte, 0, i);

                    i = bufferedInputStream.read(arrayOfByte);
                }

            } catch (Exception exception) {

                exception.printStackTrace();
            } finally {

                if (bufferedInputStream != null)
                    try {

                        bufferedInputStream.close();

                    } catch (IOException iOException) {

                        iOException.printStackTrace();
                    }

                if (fileInputStream != null)
                    try {

                        fileInputStream.close();

                    } catch (IOException iOException) {

                        iOException.printStackTrace();
                    }

                (new Thread(this, str5) {
                    public void run() {
                        try {

                            Thread.sleep(10000L);

                            FileUtil.del(this.a);

                        } catch (InterruptedException interruptedException) {

                            interruptedException.printStackTrace();
                        }
                    }

                }).start();
            }
        }
    }

    @GetMapping({"/getTreeData/{code}"})
    @PermissionData
    public Result<Map<String, Object>> e(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {

        Result<Map<String, Object>> result = new Result();

        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString);

        if (onlCgformHead == null) {

            result.error500("实体不存在");

            return result;
        }
        try {

            String str1 = onlCgformHead.getTableName();

            String str2 = onlCgformHead.getTreeIdField();

            String str3 = onlCgformHead.getTreeParentIdField();

            ArrayList arrayList = Lists.newArrayList((Object[]) new String[]{str2, str3});

            Map<String, Object> map = bConstant.getId(paramHttpServletRequest);

            String str4 = null;

            if (map.get(str2) != null)
                str4 = map.get(str2).toString();

            if (map.get("hasQuery") != null && "false".equals(map.get("hasQuery")) && map.get(str3) == null) {

                map.put(str3, "0");
            } else {

                map.put("pageSize", -521);

                map.put(str3, map.get(str3));
            }

            map.put(str2, null);
            Map<String, Object> map1 = this.onlCgformFieldService.queryAutoTreeNoPage(str1, paramString, map, arrayList, str3);
            a(onlCgformHead, map1);
            result.setResult(map1);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("数据库查询失败" + exception.getMessage());
        }
        result.setOnlTable(onlCgformHead.getTableName());
        return result;
    }

    private void a(OnlCgformHead paramOnlCgformHead, Map<String, Object> paramMap) throws BusinessException {

        List list = (List) paramMap.get("records");

        this.onlCgformHeadService.executeEnhanceList(paramOnlCgformHead, "query", list);
    }

    @PostMapping({"/crazyForm/{name}"})
    public Result<?> b(@PathVariable("name") String paramString, @RequestBody JSONObject paramJSONObject) {

        Result<String> result = new Result();
        try {

            String str = bConstant.getId();

            paramJSONObject.put("id", str);

            this.onlCgformHeadService.addCrazyFormData(paramString, paramJSONObject);

            result.setResult(str);

            result.setMessage("保存成功");

        } catch (Exception exception) {

            a.error("OnlCgformApiController.formAddForDesigner()发生异常：" + exception.getMessage(), exception);

            return Result.error("保存失败");
        }

        return result;
    }

    @PutMapping({"/crazyForm/{name}"})
    public Result<?> c(@PathVariable("name") String paramString, @RequestBody JSONObject paramJSONObject) {
        try {

            paramJSONObject.remove("create_by");

            paramJSONObject.remove("create_time");

            paramJSONObject.remove("update_by");

            paramJSONObject.remove("update_time");

            this.onlCgformHeadService.editCrazyFormData(paramString, paramJSONObject);

        } catch (Exception exception) {

            a.error("OnlCgformApiController.formEditForDesigner()发生异常：" + exception.getMessage(), exception);

            return Result.error("保存失败");
        }

        return Result.ok("保存成功!");
    }

    @AutoLog(operateType = 1, value = "online列表加载", module = ModuleType.ONLINE)
    @GetMapping({"/getErpColumns/{code}"})
    public Result<Map<String, Object>> c(@PathVariable("code") String paramString) {

        Result<Map<String, Object>> result = new Result();
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(paramString);
        if (onlCgformHead == null) {
            result.error500("实体不存在");
            return result;
        }
        HashMap<String, Object> hashMap = new HashMap<>(5);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Col b = this.onlineService.queryOnlineConfig(onlCgformHead, loginUser.getUsername());
        hashMap.put("main", b);
        if ("erp".equals(onlCgformHead.getThemeTemplate()) && onlCgformHead.getTableType() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str)) {
                ArrayList<Col> arrayList = new ArrayList<>();
                for (String str1 : str.split(",")) {
                    OnlCgformHead onlCgformHead1 = this.onlCgformHeadService.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead1 != null)
                        arrayList.add(this.onlineService.queryOnlineConfig(onlCgformHead1, loginUser.getUsername()));
                }
                if (arrayList.size() > 0)
                    hashMap.put("subList", arrayList);
            }
        }
        result.setOnlTable(onlCgformHead.getTableName());
        result.setResult(hashMap);
        result.setSuccess(true);
        return result;
    }

    @AutoLog(operateType = 1, value = "online表单加载", module = ModuleType.ONLINE)
    @GetMapping({"/getErpFormItem/{code}"})
    public Result<?> f(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {

        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString);

        if (onlCgformHead == null)
            Result.error("表不存在");

        Result<?> result = new Result();

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        JSONObject jSONObject = this.onlineService.queryOnlineFormObj(onlCgformHead, loginUser.getUsername());

        result.setResult(b2.b(jSONObject));

        result.setOnlTable(onlCgformHead.getTableName());

        return result;
    }

    @GetMapping({"/querySelectOptions"})
    public Result<List<TreeModel>> a(@ModelAttribute aEntity parama) {

        Result<List<TreeModel>> result = new Result();
        try {

            String[] arrayOfString = {parama.getCondition(), parama.getKey(), parama.getIdField(), parama.getPidField(), parama.getTable(), parama.getTxt()};

            SqlInjectionUtil.filterContent(arrayOfString);

            List list = this.onlCgformFieldService.queryDataListByLinkDown(parama);

            result.setResult(list);

            result.setSuccess(true);

        } catch (Exception exception) {

            a.warn("online级联下拉数据加载失败：", exception.getMessage());

            result.setSuccess(false);
        }

        return result;
    }

    @GetMapping({"/data/{tableName}/queryById"})
    public JSONObject a(@PathVariable("tableName") String paramString, @RequestParam(name = "mock", required = false) Boolean paramBoolean, HttpServletRequest paramHttpServletRequest) {

        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();

        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, paramString);

        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getOne((Wrapper) lambdaQueryWrapper);

        if (onlCgformHead == null)
            throw new JeecgBootException("Online表单 " + paramString + " 不存在");
        try {

            Map map = bConstant.getId(paramHttpServletRequest);

            ArrayList<String> arrayList = new ArrayList<>();

            arrayList.add("id");

            Map<String, Object> map1 = this.onlCgformFieldService.queryAutolistPage(onlCgformHead, map, arrayList);

            a(onlCgformHead, map1);

            List<Map> list = bConstant.getId(map1.get("records"), Object.class);

            if (Boolean.TRUE.equals(paramBoolean) && (list == null || list.size() == 0)) {

                Map map2 = this.onlCgformFieldService.generateMockData(onlCgformHead.getTableName());

                list = new ArrayList<>();

                list.add(map2);

                map1.put("records", list);
            }

            JSONObject jSONObject = new JSONObject();

            jSONObject.put("data", map1.get("records"));

            return jSONObject;

        } catch (Exception exception) {

            a.error(exception.getMessage(), exception);

            throw new JeecgBootException("数据库查询失败，" + exception.getMessage());
        }
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\c\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
