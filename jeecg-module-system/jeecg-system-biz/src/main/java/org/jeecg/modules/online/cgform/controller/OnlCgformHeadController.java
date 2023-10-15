package org.jeecg.modules.online.cgform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLowApp;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.enums.CgformEnum;
import org.jeecg.common.constant.enums.LowAppAopEnum;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

@RestController("onlCgformHeadController")
@RequestMapping({"/online/cgform/head"})
public class OnlCgformHeadController {
    private static final Logger a = LoggerFactory.getLogger(OnlCgformHeadController.class);

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlCgformEnhanceService onlCgformEnhanceService;

    private static List<String> b = null;

    @Autowired
    ResourceLoader resourceLoader;

    private static String c;

    @GetMapping({"/list"})
    @PermissionData
    public Result<IPage<OnlCgformHead>> a(OnlCgformHead paramOnlCgformHead, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
        Result<IPage<OnlCgformHead>> result = new Result();
        QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgformHead, paramHttpServletRequest.getParameterMap());
        Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
        IPage iPage = this.onlCgformHeadService.page((IPage) page, (Wrapper) queryWrapper);
        if (paramOnlCgformHead.getCopyType() != null && paramOnlCgformHead.getCopyType().intValue() == 0)
            this.onlCgformHeadService.initCopyState(iPage.getRecords());
        result.setSuccess(true);
        result.setResult(iPage);
        return result;
    }

    @PostMapping({"/add"})
    public Result<OnlCgformHead> a(@RequestBody OnlCgformHead paramOnlCgformHead) {
        Result<OnlCgformHead> result = new Result();
        try {
            this.onlCgformHeadService.save(paramOnlCgformHead);
            result.success("添加成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @PutMapping({"/edit"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlCgformHead> b(@RequestBody OnlCgformHead paramOnlCgformHead) {
        Result<OnlCgformHead> result = new Result();
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramOnlCgformHead.getId());
        if (onlCgformHead == null) {
            result.error500("未找到对应实体");
        } else {
            boolean bool = this.onlCgformHeadService.updateById(paramOnlCgformHead);
            if (bool)
                result.success("修改成功!");
        }
        return result;
    }

    @AutoLowApp(action = LowAppAopEnum.DELETE, bizType = "cgform")
    @DeleteMapping({"/delete"})
    public Result<?> a(@RequestParam(name = "id", required = true) String paramString) {
        try {
            this.onlCgformHeadService.deleteRecordAndTable(paramString);
        } catch (AException a) {
            return Result.error("删除失败" + a.getMessage());
        } catch (SQLException sQLException) {
            return Result.error("删除失败" + sQLException.getMessage());
        }
        return Result.ok("删除成功!");
    }

    @DeleteMapping({"/removeRecord"})
    public Result<?> b(@RequestParam(name = "id", required = true) String paramString) {
        try {
            this.onlCgformHeadService.deleteRecord(paramString);
        } catch (AException a) {
            return Result.error("移除失败" + a.getMessage());
        } catch (SQLException sQLException) {
            return Result.error("移除失败" + sQLException.getMessage());
        }
        return Result.ok("移除成功!");
    }

    @AutoLowApp(action = LowAppAopEnum.DELETE, bizType = "cgform")
    @DeleteMapping({"/deleteBatch"})
    public Result<OnlCgformHead> a(@RequestParam(name = "ids", required = true) String paramString1, @RequestParam(name = "flag") String paramString2) {
        Result<OnlCgformHead> result = new Result();
        if (paramString1 == null || "".equals(paramString1.trim())) {
            result.error500("参数不识别！");
        } else {
            this.onlCgformHeadService.deleteBatch(paramString1, paramString2);
            if ("1".equals(paramString2)) {
                result.success("删除成功!");
            } else {
                result.success("移除成功!");
            }
        }
        return result;
    }

    @GetMapping({"/queryById"})
    public Result<OnlCgformHead> c(@RequestParam(name = "id", required = true) String paramString) {
        Result<OnlCgformHead> result = new Result();
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString);
        if (onlCgformHead == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(onlCgformHead);
            result.setSuccess(true);
        }
        return result;
    }

    @GetMapping({"/queryByTableNames"})
    public Result<?> d(@RequestParam(name = "tableNames", required = true) String paramString) {
        String[] arrayOfString = paramString.split(",");
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformHead.class)
                .in(OnlCgformHead::getTableName, Arrays.asList(arrayOfString));
        List<OnlCgformHead> list = this.onlCgformHeadService.list(lambdaQueryWrapper);
        if (list == null)
            return Result.error("未找到对应实体");
        return Result.ok(list);
    }

    @PostMapping({"/enhanceJs/{code}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> a(@PathVariable("code") String paramString, @RequestBody OnlCgformEnhanceJs paramOnlCgformEnhanceJs) {
        try {
            paramOnlCgformEnhanceJs.setCgformHeadId(paramString);
            this.onlCgformHeadService.saveEnhance(paramOnlCgformEnhanceJs);
            return Result.ok("保存成功!");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("保存失败!");
        }
    }

    @GetMapping({"/enhanceJs/{code}"})
    public Result<?> a(@PathVariable("code") String paramString, HttpServletRequest paramHttpServletRequest) {
        try {
            String str = paramHttpServletRequest.getParameter("type");
            OnlCgformEnhanceJs onlCgformEnhanceJs = this.onlCgformHeadService.queryEnhance(paramString, str);
            if (onlCgformEnhanceJs == null)
                return Result.error("查询为空");
            return Result.ok(onlCgformEnhanceJs);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("查询失败!");
        }
    }

    @PutMapping({"/enhanceJs/{code}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@PathVariable("code") String paramString, @RequestBody OnlCgformEnhanceJs paramOnlCgformEnhanceJs) {
        try {
            paramOnlCgformEnhanceJs.setCgformHeadId(paramString);
            this.onlCgformHeadService.editEnhance(paramOnlCgformEnhanceJs);
            return Result.ok("保存成功!");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("保存失败!");
        }
    }

    @GetMapping({"/enhanceButton/{formId}"})
    public Result<?> b(@PathVariable("formId") String paramString, HttpServletRequest paramHttpServletRequest) {
        try {
            List list = this.onlCgformHeadService.queryButtonList(paramString);
            if (list == null || list.size() == 0)
                return Result.error("查询为空");
            return Result.ok(list);
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("查询失败!");
        }
    }

    @GetMapping({"/enhanceSql/{formId}"})
    public Result<?> c(@PathVariable("formId") String paramString, HttpServletRequest paramHttpServletRequest) {
        List list = this.onlCgformEnhanceService.queryEnhanceSqlList(paramString);
        return Result.OK(list);
    }

    @PostMapping({"/enhanceSql/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> a(@PathVariable("formId") String paramString, @RequestBody OnlCgformEnhanceSql paramOnlCgformEnhanceSql) {
        try {
            paramOnlCgformEnhanceSql.setCgformHeadId(paramString);
            if (this.onlCgformEnhanceService.checkOnlyEnhance(paramOnlCgformEnhanceSql)) {
                this.onlCgformEnhanceService.saveEnhanceSql(paramOnlCgformEnhanceSql);
                return Result.ok("保存成功!");
            }
            return Result.error("保存失败,该按钮已存在增强配置!");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("保存失败!");
        }
    }

    @PutMapping({"/enhanceSql/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@PathVariable("formId") String paramString, @RequestBody OnlCgformEnhanceSql paramOnlCgformEnhanceSql) {
        try {
            paramOnlCgformEnhanceSql.setCgformHeadId(paramString);
            if (this.onlCgformEnhanceService.checkOnlyEnhance(paramOnlCgformEnhanceSql)) {
                this.onlCgformEnhanceService.updateEnhanceSql(paramOnlCgformEnhanceSql);
                return Result.ok("保存成功!");
            }
            return Result.error("保存失败,该按钮已存在增强配置!");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("保存失败!");
        }
    }

    @DeleteMapping({"/enhanceSql"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> e(@RequestParam(name = "id", required = true) String paramString) {
        try {
            this.onlCgformEnhanceService.deleteEnhanceSql(paramString);
            return Result.ok("删除成功");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("删除失败!");
        }
    }

    @DeleteMapping({"/deletebatchEnhanceSql"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> f(@RequestParam(name = "ids", required = true) String paramString) {
        try {
            List<String> list = Arrays.asList(paramString.split(","));
            this.onlCgformEnhanceService.deleteBatchEnhanceSql(list);
            return Result.ok("删除成功");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("删除失败!");
        }
    }

    @GetMapping({"/enhanceJava/{formId}"})
    public Result<?> a(@PathVariable("formId") String paramString, OnlCgformEnhanceJava paramOnlCgformEnhanceJava) {
        List list = this.onlCgformEnhanceService.queryEnhanceJavaList(paramString);
        return Result.OK(list);
    }

    @PostMapping({"/enhanceJava/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@PathVariable("formId") String paramString, @RequestBody OnlCgformEnhanceJava paramOnlCgformEnhanceJava) {
        try {
            if ("1".equals(paramOnlCgformEnhanceJava.getActiveStatus()) && !bConstant.getId(paramOnlCgformEnhanceJava))
                return Result.error("类实例化失败，请检查!");
            paramOnlCgformEnhanceJava.setCgformHeadId(paramString);
            String str = paramOnlCgformEnhanceJava.getButtonCode();
            if ("import".equals(str) || "export".equals(str) || "query".equals(str))
                paramOnlCgformEnhanceJava.setEvent("start");
            if (this.onlCgformEnhanceService.checkOnlyEnhance(paramOnlCgformEnhanceJava)) {
                this.onlCgformEnhanceService.saveEnhanceJava(paramOnlCgformEnhanceJava);
                return Result.ok("保存成功!");
            }
            return Result.error("保存失败：一个按钮、事件只能有一个增强！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("保存失败!");
        }
    }

    @PutMapping({"/enhanceJava/{formId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> c(@PathVariable("formId") String paramString, @RequestBody OnlCgformEnhanceJava paramOnlCgformEnhanceJava) {
        try {
            if ("1".equals(paramOnlCgformEnhanceJava.getActiveStatus()) && !bConstant.getId(paramOnlCgformEnhanceJava))
                return Result.error("类实例化失败，请检查!");
            paramOnlCgformEnhanceJava.setCgformHeadId(paramString);
            String str = paramOnlCgformEnhanceJava.getButtonCode();
            if ("import".equals(str) || "export".equals(str) || "query".equals(str))
                paramOnlCgformEnhanceJava.setEvent("start");
            if (this.onlCgformEnhanceService.checkOnlyEnhance(paramOnlCgformEnhanceJava)) {
                this.onlCgformEnhanceService.updateEnhanceJava(paramOnlCgformEnhanceJava);
                return Result.ok("保存成功!");
            }
            return Result.error("保存失败：一个按钮、事件只能有一个增强！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("保存失败!");
        }
    }

    @DeleteMapping({"/enhanceJava"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> g(@RequestParam(name = "id", required = true) String paramString) {
        try {
            this.onlCgformEnhanceService.deleteEnhanceJava(paramString);
            return Result.ok("删除成功");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("删除失败!");
        }
    }

    @DeleteMapping({"/deleteBatchEnhanceJava"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> h(@RequestParam(name = "ids", required = true) String paramString) {
        try {
            List<String> list = Arrays.asList(paramString.split(","));
            this.onlCgformEnhanceService.deleteBatchEnhanceJava(list);
            return Result.ok("删除成功");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error("删除失败!");
        }
    }

    @GetMapping({"/queryTables"})
    public Result<?> a(@RequestParam(name = "tableName", required = false) String paramString, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
        String str = JwtUtil.getUserNameByToken(paramHttpServletRequest);
        if (!"admin".equals(str))
            return Result.error("noadminauth");
        List<String> list = new ArrayList<>();
        try {
            list = DbReadTableUtil.readAllTableNames();
        } catch (SQLException sQLException) {
            a.error(sQLException.getMessage(), sQLException);
            return Result.error("同步失败，未获取数据库表信息");
        }
        bConstant.b(list);
        list = bConstant.f(list);
        List<?> list1 = this.onlCgformHeadService.queryOnlinetables();
        b();
        list.removeAll(list1);
        ArrayList<HashMap<Object, Object>> arrayList = new ArrayList<>();
        for (String str1 : list) {
            if (l(str1))
                continue;
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            hashMap.put("id", str1);
            arrayList.add(hashMap);
        }
        return Result.ok(arrayList);
    }

    @PostMapping({"/transTables/{tbnames}"})
    public Result<?> d(@PathVariable("tbnames") String paramString, HttpServletRequest paramHttpServletRequest) {
        String str = JwtUtil.getUserNameByToken(paramHttpServletRequest);
        if (!"admin".equals(str))
            return Result.error("noadminauth");
        if (oConvertUtils.isEmpty(paramString))
            return Result.error("未识别的表名信息");
        if (c != null && c.equals(paramString))
            return Result.error("不允许重复生成!");
        c = paramString;
        String[] arrayOfString = paramString.split(",");
        for (byte b = 0; b < arrayOfString.length; b++) {
            if (oConvertUtils.isNotEmpty(arrayOfString[b])) {
                Long long_ = Long.valueOf(this.onlCgformHeadService.count(Wrappers.lambdaQuery(OnlCgformHead.class)
                        .eq(OnlCgformHead::getTableName, arrayOfString[b])));
                if (long_.longValue() <= 0L)
                    this.onlCgformHeadService.saveDbTable2Online(arrayOfString[b]);
            }
        }
        c = null;
        return Result.ok("同步完成!");
    }

    @GetMapping({"/rootFile"})
    public Result<?> a() {
        JSONArray jSONArray = new JSONArray();
        File[] arrayOfFile = File.listRoots();
        for (File file : arrayOfFile) {
            JSONObject jSONObject = new JSONObject();
            if (file.isDirectory()) {
                jSONObject.put("key", file.getAbsolutePath());
                jSONObject.put("title", file.getPath());
                jSONObject.put("opened", Boolean.valueOf(false));
                JSONObject jSONObject1 = new JSONObject();
                jSONObject1.put("icon", "custom");
                jSONObject.put("scopedSlots", jSONObject1);
                jSONObject.put("isLeaf", Boolean.valueOf((file.listFiles() == null || (file.listFiles()).length == 0)));
            }
            jSONArray.add(jSONObject);
        }
        return Result.ok(jSONArray);
    }

    @GetMapping({"/fileTree"})
    public Result<?> i(@RequestParam(name = "parentPath", required = true) String paramString) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("admin"))
            throw new JeecgBootException("权限不足，只有admin角色才有权限，获取服务器目录！");
        JSONArray jSONArray = new JSONArray();
        File file = new File(paramString);
        File[] arrayOfFile = file.listFiles();
        for (File file1 : arrayOfFile) {
            if (file1.isDirectory() && oConvertUtils.isNotEmpty(file1.getPath())) {
                JSONObject jSONObject1 = new JSONObject();
                System.out.println(file1.getPath());
                jSONObject1.put("key", file1.getAbsolutePath());
                jSONObject1.put("title", file1.getPath().substring(file1.getPath().lastIndexOf(File.separator) + 1));
                jSONObject1.put("isLeaf", Boolean.valueOf((file1.listFiles() == null || (file1.listFiles()).length == 0)));
                jSONObject1.put("opened", Boolean.valueOf(false));
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("icon", "custom");
                jSONObject1.put("scopedSlots", jSONObject2);
                jSONArray.add(jSONObject1);
            }
        }
        return Result.ok(jSONArray);
    }

    @GetMapping({"/tableInfo"})
    public Result<?> j(@RequestParam(name = "code", required = true) String paramString) {
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getById(paramString);
        if (onlCgformHead == null)
            return Result.error("未找到对应实体");
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("main", onlCgformHead);
        if (onlCgformHead.getTableType().intValue() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str)) {
                ArrayList<OnlCgformHead> arrayList = new ArrayList<>();
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformHead.class)
                            .eq(OnlCgformHead::getTableName, str1);
                    OnlCgformHead onlCgformHead1 = this.onlCgformHeadService.getOne(lambdaQueryWrapper);
                    arrayList.add(onlCgformHead1);
                }
                Collections.sort(arrayList, new Comparator<>() {
                    public int compare(OnlCgformHead param1OnlCgformHead1, OnlCgformHead param1OnlCgformHead2) {
                        Integer integer1 = param1OnlCgformHead1.getTabOrderNum();
                        if (integer1 == null)
                            integer1 = 0;
                        Integer integer2 = param1OnlCgformHead2.getTabOrderNum();
                        if (integer2 == null)
                            integer2 = 0;
                        return integer1.compareTo(integer2);
                    }
                });
                hashMap.put("sub", arrayList);
            }
        }
        Integer integer = onlCgformHead.getTableType();
        if ("Y".equals(onlCgformHead.getIsTree()))
            integer = 3;
        List list = CgformEnum.getJspModelList(integer);
        hashMap.put("jspModeList", list);
        hashMap.put("projectPath", DbReadTableUtil.getProjectPath());
        return Result.ok(hashMap);
    }

    @PostMapping({"/copyOnline"})
    public Result<?> k(@RequestParam(name = "code", required = true) String paramString) {
        try {
            OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(paramString);
            if (onlCgformHead == null)
                return Result.error("未找到对应实体");
            this.onlCgformHeadService.copyOnlineTableConfig(onlCgformHead);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Result.ok();
    }

    @GetMapping({"/copyOnlineTable/{id}"})
    public Result<?> b(@PathVariable("id") String paramString1, @RequestParam(name = "tableName") String paramString2) {
        try {
            this.onlCgformHeadService.copyOnlineTable(paramString1, paramString2);
        } catch (JeecgBootException jeecgBootException) {
            return Result.error(jeecgBootException.getMessage());
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            return Result.error(exception.getMessage());
        }
        return Result.ok();
    }

    private boolean l(String paramString) {
        for (String str : b) {
            if (paramString.startsWith(str) || paramString.startsWith(str.toUpperCase()))
                return true;
        }
        return false;
    }

    private void b() {
        if (b == null) {
            Resource resource = this.resourceLoader.getResource("classpath:jeecg" + File.separator + "jeecg_config.properties");
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                String str = properties.getProperty("exclude_table");
                if (str != null)
                    b = Arrays.asList(str.split(","));
            } catch (IOException iOException) {
                iOException.printStackTrace();
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
            }
        }
    }
}
