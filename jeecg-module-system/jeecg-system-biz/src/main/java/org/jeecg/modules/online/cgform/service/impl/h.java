package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.dynamic.db.DbTypeUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.cgform.b1.bLinkConstant;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlineMapper;
import org.jeecg.modules.online.cgform.model.e;
import org.jeecg.modules.online.cgform.model.f;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlineJoinQueryService;
import org.jeecg.modules.online.config.bAttribute.eTableConfig;
import org.jeecg.modules.online.config.dUtil.bAlias;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("onlineJoinQueryService")
public class h implements IOnlineJoinQueryService {
    private static final Logger a = LoggerFactory.getLogger(h.class);

    @Autowired
    IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private OnlineMapper onlineMapper;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    public Map<String, Object> pageList(OnlCgformHead head, Map<String, Object> params, boolean ignoreSelectSubField) {
        e e = getQueryInfo(head, params, ignoreSelectSubField);
        String str = e.getSql();
        Map map1 = e.getParams();
        Map map2 = e.getTableAliasMap();
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Integer integer = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        if (integer.intValue() == -521) {
            List<Map<String, Object>> list = this.onlineMapper.selectByCondition(str, map1);
            if (list == null || list.size() == 0) {
                hashMap.put("total", 0);
            } else {
                hashMap.put("total", list.size());
                if (ignoreSelectSubField)
                    list = b(list);
                hashMap.put("records", bConstant.getId(list, map2.values()));
            }
            if (ignoreSelectSubField)
                hashMap.put("fieldList", e.getFieldList());
        } else {
            Integer integer1 = Integer.valueOf((params.get("pageNo") == null) ? 1 : Integer.parseInt(params.get("pageNo").toString()));
            Page page = new Page(integer1.intValue(), integer.intValue());
            page.setOptimizeCountSql(false);
            IPage iPage = this.onlineMapper.selectPageByCondition(page, str, map1);
            hashMap.put("total", iPage.getTotal());
            List<Map<String, Object>> list = iPage.getRecords();
            if (ignoreSelectSubField) {
                list = b(list);
            }
            hashMap.put("records", bConstant.getId(list, map2.values()));
        }
        return (Map) hashMap;
    }

    private String a(f paramf, String paramString1, String paramString2, String paramString3) {
        String str1 = paramf.getAlias();
        String str2 = paramf.getTableName();
        String str3 = bConstant.f(str2);
        String str4 = str1 + ".";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" AND EXISTS (");
        stringBuffer.append("SELECT ");
        stringBuffer.append(str4 + "id");
        stringBuffer.append(" FROM ");
        stringBuffer.append(str3);
        stringBuffer.append(" " + str1);
        stringBuffer.append(" where  ");
        stringBuffer.append(str4);
        stringBuffer.append(paramf.getJoinField());
        stringBuffer.append("=");
        stringBuffer.append(paramString1);
        stringBuffer.append(paramf.getMainField());
        if (paramString2 != null && paramString2.length() > 0)
            stringBuffer.append(paramString2);
        if (paramString3 != null && paramString3.length() > 0)
            stringBuffer.append(" AND (").append(paramString3).append(") ");
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public Map<String, Object> pageList(OnlCgformHead head, Map<String, Object> params) {
        return pageList(head, params, false);
    }

    private String a(List<String> paramList, Map<String, Integer> paramMap, Map<String, String> paramMap1) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str1 : paramList) {
            String[] arrayOfString = str1.split("\\.");
            String str2 = arrayOfString[0];
            if ("a".equals(str2)) {
                arrayList.add(str1);
                continue;
            }
            String str3 = arrayOfString[1];
            int i = paramMap.get(str3);
            if (i > 1) {
                String str = paramMap1.get(str2);
                arrayList.add(str1 + " " + str + "_" + str3);
                continue;
            }
            arrayList.add(str1);
        }
        return String.join(",", (Iterable) arrayList);
    }

    private void a(String paramString, boolean paramBoolean, List<OnlCgformField> paramList, List<String> paramList1, Map<String, Integer> paramMap) {
        if (paramList == null || paramList.size() == 0) {
            if (paramBoolean)
                paramList1.add(paramString + "id");
        } else {
            int i = paramList.size();
            for (byte b = 0; b < i; b++) {
                OnlCgformField onlCgformField = paramList.get(b);
                String str = onlCgformField.getDbFieldName();
                if (!"id".equals(str))
                    if (1 == onlCgformField.getIsShowList().intValue()) {
                        if ("cat_tree".equals(onlCgformField.getFieldShowType()) && oConvertUtils.isNotEmpty(onlCgformField.getDictText()))
                            paramList1.add(paramString + onlCgformField.getDictText());
                        paramList1.add(paramString + str);
                        Integer integer = paramMap.get(str);
                        if (integer == null) {
                            paramMap.put(str, Integer.valueOf(1));
                        } else {
                            paramMap.put(str, Integer.valueOf(1 + integer.intValue()));
                        }
                    }
            }
            paramList1.add(paramString + "id");
            paramMap.put("id", Integer.valueOf(2));
        }
    }

    private f a(OnlCgformHead paramOnlCgformHead, int paramInt, boolean paramBoolean) {
        String str1 = paramOnlCgformHead.getId();
        String str2 = paramOnlCgformHead.getTableName();
        f f = new f(str2, str1, paramBoolean);
        List<OnlCgformField> list = a(str1);
        List list1 = this.onlCgformFieldService.queryAvailableFields(str1, str2, true, list, null);
        f.setAllFieldList(list);
        f.setSelectFieldList(list1);
        f.setAliasByIntValue(paramInt);
        if (!paramBoolean)
            for (OnlCgformField onlCgformField : list) {
                if (oConvertUtils.isNotEmpty(onlCgformField.getMainField()) && oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
                    f.setMainField(onlCgformField.getMainField());
                    f.setJoinField(onlCgformField.getDbFieldName());
                    break;
                }
            }
        return f;
    }

    private List<f> a(OnlCgformHead paramOnlCgformHead, String paramString) {
        byte b = 97;
        ArrayList<f> arrayList = new ArrayList<>();
        f f = a(paramOnlCgformHead, b++, true);
        List list = this.onlAuthDataService.queryUserOnlineAuthData(paramString, paramOnlCgformHead.getId());
        f.setAuthList(list);
        arrayList.add(f);
        Integer integer = paramOnlCgformHead.getTableType();
        if (integer != null && integer.intValue() == 2) {
            String str = paramOnlCgformHead.getSubTableStr();
            if (str != null && !"".equals(str)) {
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead != null) {
                        f f1 = a(onlCgformHead, b++, false);
                        List<SysPermissionDataRuleModel> list1 = this.onlAuthDataService.queryUserOnlineAuthData(paramString, onlCgformHead.getId());
                        f1.setAuthList(list1);
                        arrayList.add(f1);
                    }
                }
            }
        }
        return arrayList;
    }

    private Map<String, List<OnlCgformField>> a(OnlCgformHead paramOnlCgformHead, Map<String, String> paramMap) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        paramMap.put(paramOnlCgformHead.getTableName(), paramOnlCgformHead.getId());
        List<OnlCgformField> list = a(paramOnlCgformHead.getId());
        hashMap.put(paramOnlCgformHead.getTableName(), list);
        Integer integer = paramOnlCgformHead.getTableType();
        if (integer != null && integer.intValue() == 2) {
            String str = paramOnlCgformHead.getSubTableStr();
            if (str != null && !"".equals(str)) {
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(Wrappers.lambdaQuery(OnlCgformHead.class)
                            .eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead != null) {
                        paramMap.put(onlCgformHead.getTableName(), onlCgformHead.getId());
                        List<OnlCgformField> list1 = a(onlCgformHead.getId());
                        hashMap.put(onlCgformHead.getTableName(), list1);
                    }
                }
            }
        }
        return (Map) hashMap;
    }

    private List<OnlCgformField> a(String paramString) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, paramString)
                .eq(OnlCgformField::getDbIsPersist, bLinkConstant.b)
                .orderByAsc(OnlCgformField::getOrderNum);
        return this.onlCgformFieldService.list(lambdaQueryWrapper);
    }

    private boolean a(Map<String, Object> paramMap, boolean paramBoolean, String paramString1, String paramString2, List<OnlCgformField> paramList, List<org.jeecg.modules.online.cgform.model.h> paramList1) {
        boolean bool = paramBoolean ? true : false;
        Object object = paramMap.get("column");
        if (object != null && !"id".equals(object.toString())) {
            String str1 = object.toString();
            Object object1 = paramMap.get("order");
            String str2 = "desc";
            if (object1 != null)
                str2 = object1.toString();
            if (paramBoolean) {
                if (bConstant.c(str1, paramList)) {
                    org.jeecg.modules.online.cgform.model.h h1 = new org.jeecg.modules.online.cgform.model.h(str1, str2);
                    h1.setAlias(paramString2);
                    paramList1.add(h1);
                }
            } else if (str1.startsWith(paramString1)) {
                String str = str1.replaceFirst(paramString1 + "_", "");
                if (bConstant.c(str, paramList)) {
                    org.jeecg.modules.online.cgform.model.h h1 = new org.jeecg.modules.online.cgform.model.h(str, str2);
                    h1.setAlias(paramString2);
                    paramList1.add(h1);
                    bool = true;
                }
            }
        } else {
            for (OnlCgformField onlCgformField : paramList) {
                if ("1".equals(onlCgformField.getSortFlag())) {
                    String str = onlCgformField.getFieldExtendJson();
                    org.jeecg.modules.online.cgform.model.h h1 = new org.jeecg.modules.online.cgform.model.h(onlCgformField.getDbFieldName());
                    h1.setAlias(paramString2);
                    if (str != null && !"".equals(str)) {
                        JSONObject jSONObject = JSON.parseObject(str);
                        String str1 = jSONObject.getString("orderRule");
                        if (str1 != null && !"".equals(str1)) {
                            h1.setRule(str1);
                            paramList1.add(h1);
                            bool = true;
                        }
                    }
                }
            }
        }
        return bool;
    }

    private String a(List<org.jeecg.modules.online.cgform.model.h> paramList) {
        if (paramList.size() == 0) {
            org.jeecg.modules.online.cgform.model.h h1 = org.jeecg.modules.online.cgform.model.h.a("a.");
            paramList.add(h1);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (org.jeecg.modules.online.cgform.model.h h1 : paramList) {
            String str = h1.getRealSql();
            arrayList.add(str);
        }
        return " ORDER BY " + String.join(",", arrayList);
    }

    private String a(StringBuilder paramStringBuilder) {
        String str = paramStringBuilder.toString();
        if (str == null || "".equals(str))
            return "";
        return " AND (" + str + ") ";
    }

    private boolean a(StringBuilder paramStringBuilder, JSONArray paramJSONArray, MatchTypeEnum paramMatchTypeEnum, String paramString1, String paramString2, List<OnlCgformField> paramList, boolean paramBoolean1, boolean paramBoolean2) {
        boolean bool = paramBoolean2;
        if (paramJSONArray != null)
            for (byte b = 0; b < paramJSONArray.size(); b++) {
                JSONObject jSONObject = paramJSONArray.getJSONObject(b);
                String str = jSONObject.getString("field");
                String[] arrayOfString = str.split(",");
                if (arrayOfString.length == 1) {
                    if (paramBoolean1 && bConstant.c(str, paramList)) {
                        String str1 = paramString1 + str;
                        bConstant.getId(paramStringBuilder, str1, jSONObject, paramMatchTypeEnum, null, bool);
                        bool = false;
                    }
                } else {
                    String str1 = arrayOfString[1];
                    if (paramString2.equalsIgnoreCase(arrayOfString[0]) &&
                            bConstant.c(str1, paramList)) {
                        String str2 = paramString1 + str1;
                        bConstant.getId(paramStringBuilder, str2, jSONObject, paramMatchTypeEnum, null, bool);
                        bool = false;
                    }
                }
            }
        return bool;
    }

    private List<Map<String, Object>> b(List<Map<String, Object>> paramList) {
        HashMap<String, Map<String, Object>> hashMap = new HashMap<>(5);
        for (Map<String, Object> map : paramList) {
            String str = map.get("id").toString();
            if (hashMap.get(str) == null) {
                hashMap.put(str, map);
            }
        }
        return new ArrayList<>(hashMap.values());
    }

    private boolean a(f paramf, JSONArray paramJSONArray) {
        if (paramf.a())
            return true;
        String str = paramf.getTableName();
        if (paramJSONArray != null && paramJSONArray.size() > 0)
            for (byte b = 0; b < paramJSONArray.size(); b++) {
                JSONObject jSONObject = paramJSONArray.getJSONObject(b);
                String str1 = jSONObject.getString("field");
                String[] arrayOfString = str1.split(",");
                if (arrayOfString.length == 2 &&
                        arrayOfString[0] != null && arrayOfString[0].equals(str))
                    return true;
            }
        return false;
    }

    private boolean a(f paramf) {
        if (paramf.a())
            return true;
        List<OnlCgformField> list = paramf.getSelectFieldList();
        if (list != null && list.size() > 0)
            for (OnlCgformField onlCgformField : list) {
                String str = onlCgformField.getMainTable();
                if (str == null || "".equals(str))
                    return true;
            }
        return false;
    }

    private bAlias a(f paramf, JSONArray paramJSONArray, String paramString, bAlias paramb) {
        String str = paramf.getTableName();
        boolean bool = paramf.a();
        List<OnlCgformField> list = paramf.getAllFieldList();
        ArrayList<eTableConfig> arrayList = new ArrayList<>();
        if (paramJSONArray != null) {
            for (byte b1 = 0; b1 < paramJSONArray.size(); b1++) {
                JSONObject jSONObject = paramJSONArray.getJSONObject(b1);
                String str1 = jSONObject.getString("field");
                String[] arrayOfString = str1.split(",");
                if (arrayOfString.length == 1) {
                    if (bool && bConstant.c(str1, list)) {
                        eTableConfig e = new eTableConfig(jSONObject);
                        arrayList.add(e);
                    }
                } else {
                    String str2 = arrayOfString[1];
                    if (str.equalsIgnoreCase(arrayOfString[0]) &&
                            bConstant.c(str2, list)) {
                        eTableConfig e = new eTableConfig(jSONObject);
                        arrayList.add(e);
                    }
                }
            }
            if (arrayList.size() > 0) {
                String str1 = paramf.getAlias() + ".";
                bAlias b2 = new bAlias(str1, true, paramString);
                b2.setDuplicateSqlNameRecord(paramb.getDuplicateSqlNameRecord());
                b2.setDuplicateParamNameRecord(paramb.getDuplicateParamNameRecord());
                b2.a(arrayList);
                return b2;
            }
        }
        return null;
    }

    public e getQueryInfo(OnlCgformHead head, Map<String, Object> params, boolean ignoreSelectSubField) {
        return getQueryInfo(head, params, ignoreSelectSubField, false);
    }

    public e getQueryInfo(OnlCgformHead head, Map<String, Object> params, boolean ignoreSelectSubField, boolean isNewExport) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<f> list = a(head, loginUser.getId());
        JSONArray jSONArray = bConstant.b(params);
        MatchTypeEnum matchTypeEnum = bConstant.c(params);
        StringBuilder stringBuilder = new StringBuilder();
        boolean bool1 = true;
        String str1 = "";
        boolean bool2 = false;
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<org.jeecg.modules.online.cgform.model.h> arrayList1 = new ArrayList<>();
        HashMap<Object, Object> hashMap1 = new HashMap<>(5);
        HashMap<Object, Object> hashMap2 = new HashMap<>(5);
        List list1 = new ArrayList<>();
        HashMap<Object, Object> hashMap3 = new HashMap<>(5);
        for (f f : list) {
            List<OnlCgformField> list2 = f.getSelectFieldList();
            String str6 = f.getAlias();
            String str7 = str6 + ".";
            String str8 = " " + str6 + " ";
            String str9 = f.getTableName();
            List<OnlCgformField> list3 = f.getAllFieldList();
            List list4 = f.getAuthList();
            if (!bool2 && list4 != null && list4.size() > 0) {
                JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
                bool2 = true;
            }
            bAlias b1 = new bAlias(str7);
            b1.setTableName(str9);
            b1.setNeedList(null);
            b1.setFirst(false);
            List<eTableConfig> list5 = bConstant.g(list3);
            String str10 = b1.a(list5, params, list4, str9 + "@");
            Map<?, ?> map = b1.getSqlParams();
            hashMap3.putAll(map);
            boolean bool3 = a(params, f.a(), str9, str7, list3, arrayList1);
            boolean bool4 = a(f);
            boolean bool5 = a(f, jSONArray);
            boolean bool6 = (str10.length() > 0) ? true : false;
            boolean bool7 = (bool4 || bool5 || bool6 || bool3) ? true : false;
            if (!bool7)
                continue;
            boolean bool8 = (!bool4 && (bool5 || bool6)) ? true : false;
            if (bool3)
                bool8 = false;
            if ((ignoreSelectSubField && f.a()) || (!ignoreSelectSubField && bool4))
                a(str7, f.a(), list2, arrayList, (Map) hashMap1);
            String str11 = "";
            bAlias b2 = a(f, jSONArray, matchTypeEnum.getValue(), b1);
            if (b2 != null) {
                str11 = b2.getSql().toString();
                if (str11.length() > 0)
                    hashMap3.putAll(b2.getSqlParams());
            }
            if (f.a()) {
                stringBuffer1.append(" FROM " + bConstant.f(str9) + str8);
                str1 = str7;
            } else {
                hashMap2.put(str6, str9);
                if (bool8) {
                    String str = a(f, str1, str10, str11);
                    stringBuffer2.append(str);
                } else {
                    stringBuffer1.append(" LEFT JOIN ");
                    stringBuffer1.append(bConstant.f(str9));
                    stringBuffer1.append(str8);
                    stringBuffer1.append(" ON ");
                    stringBuffer1.append(str7);
                    stringBuffer1.append(f.getJoinField());
                    stringBuffer1.append("=");
                    stringBuffer1.append(str1);
                    stringBuffer1.append(f.getMainField());
                }
            }
            if (!bool8) {
                stringBuffer2.append(str10);
                if (str11.length() > 0) {
                    if (bool1) {
                        stringBuilder.append(str11);
                        bool1 = false;
                        continue;
                    }
                    stringBuilder.append(" ").append(matchTypeEnum.getValue()).append(" ").append(str11);
                }
            }
        }
        String str2 = a(arrayList, (Map) hashMap1, (Map) hashMap2);
        String str3 = a(stringBuilder);
        String str4 = a(arrayList1);
        String str5 = "SELECT " + str2 + stringBuffer1.toString() + " where 1=1  " + stringBuffer2.toString() + str3;
        DbType dbType = e.c(null);
        if (!DbTypeUtils.dbTypeIsSqlServer(dbType))
            str5 = str5 + str4;
        e e = new e(str5, hashMap3);
        e.setTableAliasMap(hashMap2);
        for (f f : list) {
            List list2 = f.getSelectFieldList();
            if (isNewExport) {
                for (OnlCgformField onlCgformField : list2) {
                    String str = onlCgformField.getDbFieldName();
                    Integer integer = (Integer) hashMap1.get(str);
                    if (integer != null && integer.intValue() > 1 && !f.a())
                        onlCgformField.setDbFieldName(f.getTableName() + "_" + str);
                    list1.add(onlCgformField);
                }
                continue;
            }
            if (ignoreSelectSubField && f.a())
                list1 = list2;
        }
        e.setFieldList(list1);
        return e;
    }

    public XSSFWorkbook handleOnlineExport(OnlCgformHead head, Map<String, Object> params) {
        XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
        boolean bool = bConstant.getId(head);
        e e = null;
        if (bool) {
            e = getQueryInfo(head, params, false, true);
        } else {
            e = this.onlCgformFieldService.getQueryInfo(head, params, null);
        }
        boolean bool1 = true;
        Integer integer1 = Integer.valueOf(50000);
        Integer integer2 = Integer.valueOf(1);
        String str = e.getSql();
        Map map = e.getParams();
        List list = e.getFieldList();
        List<ExcelExportEntity> list1 = bConstant.getId(list, "id", this.upLoadPath);
        boolean bool2 = false;
        while (bool1) {
            Page page = new Page(integer2.intValue(), integer1.intValue());
            page.setOptimizeCountSql(false);
            page.setSearchCount(false);
            Integer integer3 = integer2, integer4 = integer2 = Integer.valueOf(integer2.intValue() + 1);
            params.put("pageNo", integer3);
            IPage iPage = this.onlineMapper.selectPageByCondition(page, str, map);
            List list2 = bLinkConstant.d(iPage.getRecords());
            if (list2 == null || list2.size() == 0) {
                bool1 = false;
                continue;
            }
            List<Map<String, Object>> list3 = new ArrayList<>();
            String str1 = (params.get("selections") == null) ? null : params.get("selections").toString();
            if (oConvertUtils.isNotEmpty(str1)) {
                bool1 = false;
                if (bool) {
                    Map map1 = e.getTableAliasMap();
                    ArrayList arrayList = new ArrayList(map1.values());
                    Map map2 = bConstant.f(str1, arrayList);
                    List list4 = (List) list2.stream().filter(paramMap2 -> a(paramMap2, paramMap1)).collect(Collectors.toList());
                } else {
                    List list4 = bLinkConstant.h(str1);
                    list3 = (List) list2.stream().filter(paramMap -> paramList.contains(paramMap.get("id"))).collect(Collectors.toList());
                }
            } else {
                if (list2 == null)
                    list2 = new ArrayList<>();
                list3.addAll(list2);
            }
            bConstant.getId(1, list3, list);
            try {
                this.onlCgformHeadService.executeEnhanceExport(head, list3);
            } catch (BusinessException businessException) {
                a.error("导出java增强处理出错", businessException.getMessage());
            }
            if (head.getTableType().intValue() == 2 && !bool)
                if (oConvertUtils.isEmpty(params.get("exportSingleOnly"))) {
                    String str2 = head.getSubTableStr();
                    if (oConvertUtils.isNotEmpty(str2)) {
                        String[] arrayOfString = str2.split(",");
                        for (String str3 : arrayOfString)
                            addAllSubTableDate(str3, params, list3, list1, bool2);
                        bool2 = true;
                    }
                }
            ExcelExportServer excelExportServer = new ExcelExportServer();
            ExportParams exportParams = new ExportParams();
            exportParams.setType(ExcelType.XSSF);
            excelExportServer.createSheetForMap((Workbook) xSSFWorkbook, exportParams, list1, list3);
        }
        return xSSFWorkbook;
    }

    public void addAllSubTableDate(String subTable, Map<String, Object> params, List<Map<String, Object>> result, List<ExcelExportEntity> entityList, boolean subEntityExist) {
        if (oConvertUtils.isEmpty(subTable))
            return;
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadService.getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, subTable));
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list = this.onlCgformFieldService.list((Wrapper) lambdaQueryWrapper);
        String str1 = "", str2 = "";
        for (OnlCgformField onlCgformField : list) {
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                str1 = onlCgformField.getMainField();
                str2 = onlCgformField.getDbFieldName();
                break;
            }
        }
        if (!subEntityExist) {
            ExcelExportEntity excelExportEntity = new ExcelExportEntity(onlCgformHead.getTableTxt(), subTable);
            excelExportEntity.setList(bConstant.getId(list, "id", this.upLoadPath));
            entityList.add(excelExportEntity);
        }
        for (byte b = 0; b < result.size(); b++) {
            params.put(str2, ((Map) result.get(b)).get(str1));
            String str = b.a(onlCgformHead.getTableName(), list, params);
            List list1 = this.onlCgformHeadService.queryListData(str);
            b.a(1, list1, list);
            ((Map<String, List>) result.get(b)).put(subTable, b.d(list1));
        }
    }

    private boolean a(Map<String, Object> paramMap, Map<String, List<String>> paramMap1) {
        boolean bool = true;
        for (String str : paramMap1.keySet()) {
            List list = paramMap1.get(str);
            bool = (bool && list.contains(paramMap.get(str))) ? true : false;
        }
        return bool;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\a\h.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
