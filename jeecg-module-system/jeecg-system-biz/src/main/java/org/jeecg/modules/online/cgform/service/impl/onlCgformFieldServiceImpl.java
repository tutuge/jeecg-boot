package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.dto.DataLogDTO;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.a1.aEntity;
import org.jeecg.modules.online.cgform.b1.bLinkConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.mapper.OnlineMapper;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.model.e;
import org.jeecg.modules.online.cgform.model.h;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service("onlCgformFieldServiceImpl")
public class onlCgformFieldServiceImpl extends ServiceImpl<OnlCgformFieldMapper, OnlCgformField> implements IOnlCgformFieldService {
    private static final Logger b = LoggerFactory.getLogger(onlCgformFieldServiceImpl.class);

    @Autowired
    private OnlCgformFieldMapper onlCgformFieldMapper;

    @Autowired
    private OnlCgformHeadMapper cgformHeadMapper;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private OnlineMapper onlineMapper;

    private static final String c = "0";

    public static ExecutorService a = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    public Map<String, Object> queryAutolistPage(OnlCgformHead head, Map<String, Object> params, List<String> needList) {
        e e = getQueryInfo(head, params, needList);
        String str = e.getSql();
        Map map = e.getParams();
        List list = e.getFieldList();
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Integer integer = Integer.valueOf((params.get("pageSize") == null) ? 10 : Integer.parseInt(params.get("pageSize").toString()));
        if (integer.intValue() == -521) {
            List list1 = this.onlineMapper.selectByCondition(str, map);
            if (list1 == null || list1.size() == 0) {
                hashMap.put("total", Integer.valueOf(0));
                hashMap.put("fieldList", list);
            } else {
                hashMap.put("total", Integer.valueOf(list1.size()));
                hashMap.put("fieldList", list);
                hashMap.put("records", b.d(list1));
            }
        } else {
            Integer integer1 = Integer.valueOf((params.get("pageNo") == null) ? 1 : Integer.parseInt(params.get("pageNo").toString()));
            Page page = new Page(integer1.intValue(), integer.intValue());
            IPage iPage = this.onlineMapper.selectPageByCondition(page, str, map);
            hashMap.put("total", Long.valueOf(iPage.getTotal()));
            List<Map<String, Object>> list1 = b.d(iPage.getRecords());
            handleLinkTableDictData(head.getId(), list1);
            hashMap.put("records", list1);
        }
        return (Map) hashMap;
    }

    public Map<String, Object> queryAutoTreeNoPage(String tbname, String headId, Map<String, Object> params, List<String> needList, String pidField) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, headId);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list1 = list((Wrapper) lambdaQueryWrapper);
        List<OnlCgformField> list2 = queryAvailableFields(headId, tbname, true, list1, needList);
        StringBuffer stringBuffer = new StringBuffer();
        b.a(tbname, list2, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str1 = loginUser.getId();
        List list3 = this.onlAuthDataService.queryUserOnlineAuthData(str1, headId);
        if (list3 != null && list3.size() > 0)
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        bLinkConstant b = new bLinkConstant("t.");
        b.setTableName(tbname);
        b.setNeedList(needList);
        b.setSubTableStr("");
        List list4 = b.g(list1);
        String str2 = b.a(list4, params, list3);
        Map map = b.getSqlParams();
        if (str2.trim().length() > 0)
            stringBuffer.append(" t ").append(" where  ").append(str2);
        String str3 = a(list1, params);
        stringBuffer.append(str3);
        Integer integer = Integer.valueOf((params.get("pageSize") == null) ? 10 : Integer.parseInt(params.get("pageSize").toString()));
        if (integer.intValue() == -521) {
            List<Map<String, Object>> list = this.onlineMapper.selectByCondition(stringBuffer.toString(), map);
            if ("true".equals(params.get("hasQuery"))) {
                ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                for (Map<String, Object> map1 : (Iterable<Map<String, Object>>) list) {
                    String str = b.a(map1, pidField);
                    if (str != null && !"0".equals(str)) {
                        Map<String, Object> map2 = a(str, tbname, headId, needList, pidField);
                        if (map2 != null && map2.size() > 0 && !arrayList.contains(map2))
                            arrayList.add(map2);
                        continue;
                    }
                    if (!arrayList.contains(map1))
                        arrayList.add(map1);
                }
                list = arrayList;
            }
            if (list == null || list.size() == 0) {
                hashMap.put("total", Integer.valueOf(0));
                hashMap.put("fieldList", list2);
            } else {
                hashMap.put("total", Integer.valueOf(list.size()));
                hashMap.put("fieldList", list2);
                hashMap.put("records", b.d(list));
            }
        } else {
            Integer integer1 = Integer.valueOf((params.get("pageNo") == null) ? 1 : Integer.parseInt(params.get("pageNo").toString()));
            Page page = new Page(integer1.intValue(), integer.intValue());
            IPage iPage = this.onlineMapper.selectPageByCondition(page, stringBuffer.toString(), map);
            hashMap.put("total", Long.valueOf(iPage.getTotal()));
            hashMap.put("records", b.d(iPage.getRecords()));
        }
        return (Map) hashMap;
    }

    private Map<String, Object> a(String paramString1, String paramString2, String paramString3, List<String> paramList, String paramString4) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("id", paramString1);
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, paramString3);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list1 = list((Wrapper) lambdaQueryWrapper);
        List<OnlCgformField> list2 = queryAvailableFields(paramString3, paramString2, true, list1, paramList);
        StringBuffer stringBuffer = new StringBuffer();
        b.a(paramString2, list2, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str1 = loginUser.getId();
        List list3 = this.onlAuthDataService.queryUserOnlineAuthData(str1, paramString3);
        if (list3 != null && list3.size() > 0)
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        bLinkConstant b = new bLinkConstant("t.");
        b.setTableName(paramString2);
        b.setNeedList(paramList);
        b.setSubTableStr("");
        List list4 = b.g(list1);
        String str2 = b.a(list4, hashMap, list3);
        Map map = b.getSqlParams();
        stringBuffer.append(" t ").append(" where  ").append(" id = '").append(paramString1).append("' ");
        if (str2.trim().length() > 0)
            stringBuffer.append(" and ").append(str2);
        List<Map> list = this.onlineMapper.selectByCondition(stringBuffer.toString(), map);
        if (list != null && list.size() > 0) {
            Map<String, Object> map1 = list.get(0);
            if (map1 != null && map1.get(paramString4) != null && !"0".equals(map1.get(paramString4)))
                return a(map1.get(paramString4).toString(), paramString2, paramString3, paramList, paramString4);
            return map1;
        }
        return null;
    }

    public void saveFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List list = list((Wrapper) lambdaQueryWrapper);
        if (isCrazy) {
            Map map = b.c(tbname, list, json);
            addOnlineInsertDataLog(tbname, map.get("id").toString());
            ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(map);
        } else {
            Map map = b.a(tbname, list, json);
            addOnlineInsertDataLog(tbname, map.get("id").toString());
            ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(map);
        }
    }

    public void saveTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List list = list((Wrapper) lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            if (hasChildField.equals(onlCgformField.getDbFieldName()) && onlCgformField.getIsShowForm().intValue() != 1) {
                onlCgformField.setIsShowForm(Integer.valueOf(1));
                json.put(hasChildField, "0");
                continue;
            }
            if (pidField.equals(onlCgformField.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
                onlCgformField.setIsShowForm(Integer.valueOf(1));
                json.put(pidField, "0");
            }
        }
        Map map = b.a(tbname, list, json);
        addOnlineInsertDataLog(tbname, map.get("id").toString());
        ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(map);
        if (!"0".equals(json.getString(pidField)))
            ((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + tbname + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + "'");
    }

    public void saveFormData(List<OnlCgformField> fieldList, String tbname, JSONObject json) {
        Map map = b.a(tbname, fieldList, json);
        this.onlCgformFieldMapper.executeInsertSQL(map);
    }

    public void editFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list((Wrapper) lambdaQueryWrapper);
        if (isCrazy) {
            Map map = b.d(tbname, list, json);
            addOnlineUpdateDataLog(tbname, map.get("id").toString(), list, json);
            this.onlCgformFieldMapper.executeUpdatetSQL(map);
        } else {
            Map map = b.b(tbname, list, json);
            addOnlineUpdateDataLog(tbname, map.get("id").toString(), list, json);
            this.onlCgformFieldMapper.executeUpdatetSQL(map);
        }
    }

    public void editTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
        String str1 = b.f(tbname);
        String str2 = "select * from " + str1 + " where id = '" + json.getString("id") + "'";
        Map map1 = ((OnlCgformFieldMapper) this.baseMapper).queryFormData(str2);
        Map map2 = b.a(map1);
        String str3 = map2.get(pidField).toString();
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list((Wrapper) lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            if (pidField.equals(onlCgformField.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
                onlCgformField.setIsShowForm(Integer.valueOf(1));
                json.put(pidField, "0");
            }
        }
        Map map3 = b.b(tbname, list, json);
        addOnlineUpdateDataLog(tbname, map3.get("id").toString(), list, json);
        ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(map3);
        if (!str3.equals(json.getString(pidField))) {
            if (!"0".equals(str3)) {
                String str = "select count(*) from " + str1 + " where " + pidField + " = '" + str3 + "'";
                Integer integer = ((OnlCgformFieldMapper) this.baseMapper).queryCountBySql(str);
                if (integer == null || integer.intValue() == 0)
                    ((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + str1 + " set " + hasChildField + " = '0' where id = '" + str3 + "'");
            }
            if (!"0".equals(json.getString(pidField)))
                ((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + str1 + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + "'");
        }
    }

    public Map<String, Object> queryFormData(String code, String tbname, String id) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, Integer.valueOf(1));
        List list = list((Wrapper) lambdaQueryWrapper);
        String str = b.a(tbname, list, id);
        return this.onlCgformFieldMapper.queryFormData(str);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteAutoListMainAndSub(OnlCgformHead head, String ids) {
        if (head.getTableType().intValue() == 2) {
            String str1 = head.getId();
            String str2 = head.getTableName();
            String str3 = "tableName";
            String str4 = "linkField";
            String str5 = "linkValueStr";
            String str6 = "mainField";
            ArrayList<HashMap<Object, Object>> arrayList = new ArrayList<>();
            if (oConvertUtils.isNotEmpty(head.getSubTableStr())) {
                for (String str : head.getSubTableStr().split(",")) {
                    OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null) {
                        LambdaQueryWrapper lambdaQueryWrapper1 = (LambdaQueryWrapper) ((LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId())).eq(OnlCgformField::getMainTable, head.getTableName());
                        List<OnlCgformField> list1 = list((Wrapper) lambdaQueryWrapper1);
                        if (list1 != null && list1.size() != 0) {
                            OnlCgformField onlCgformField = list1.get(0);
                            HashMap<Object, Object> hashMap = new HashMap<>(5);
                            hashMap.put(str4, onlCgformField.getDbFieldName());
                            hashMap.put(str6, onlCgformField.getMainField());
                            hashMap.put(str3, str);
                            hashMap.put(str5, "");
                            arrayList.add(hashMap);
                        }
                    }
                }
                LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str1);
                List list = list((Wrapper) lambdaQueryWrapper);
                String[] arrayOfString = ids.split(",");
                for (String str7 : arrayOfString) {
                    if (str7.indexOf("@") > 0)
                        str7 = str7.substring(0, str7.indexOf("@"));
                    String str8 = b.a(str2, list, str7);
                    Map map = this.onlCgformFieldMapper.queryFormData(str8);
                    ArrayList arrayList1 = new ArrayList<>();
                    for (Map<String, String> map1 : arrayList) {
                        Object object = map.get(((String) map1.get(str6)).toLowerCase());
                        if (object == null)
                            object = map.get(((String) map1.get(str6)).toUpperCase());
                        if (object == null)
                            continue;
                        String str = (String) map1.get(str5) + String.valueOf(object) + ",";
                        map1.put(str5, str);
                    }
                }
                for (Map<Object, Object> map : arrayList)
                    deleteAutoList((String) map.get(str3), (String) map.get(str4), (String) map.get(str5));
            }
            deleteAutoListById(head.getTableName(), ids);
        }
    }

    public void deleteAutoListById(String tbname, String ids) {
        deleteAutoList(tbname, "id", ids);
    }

    public void deleteAutoList(String tbname, String linkField, String linkValue) {
        if (linkValue != null && !"".equals(linkValue)) {
            String[] arrayOfString = linkValue.split(",");
            StringBuffer stringBuffer = new StringBuffer();
            for (String str : arrayOfString) {
                if (str != null && !"".equals(str)) {
                    if (str.indexOf("@") > 0)
                        str = str.substring(0, str.indexOf("@"));
                    stringBuffer.append("'" + str + "',");
                }
            }
            String str1 = stringBuffer.toString();
            String str2 = "DELETE FROM " + b.f(tbname) + " where " + linkField + " in(" + str1.substring(0, str1.length() - 1) + ")";
            this.onlCgformFieldMapper.deleteAutoList(str2);
        }
    }

    public List<Map<String, String>> getAutoListQueryInfo(String code) {
        int i = 0;
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getId, code));
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        boolean bool = b.a(onlCgformHead);
        i = a(onlCgformHead, arrayList, i, bool);
        Integer integer = onlCgformHead.getTableType();
        if (bool && integer != null && 2 == integer.intValue()) {
            String str = onlCgformHead.getSubTableStr();
            if (str != null && !"".equals(str)) {
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    OnlCgformHead onlCgformHead1 = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead1 != null)
                        i = a(onlCgformHead1, arrayList, i, true);
                }
            }
        }
        return arrayList;
    }

    public List<OnlCgformField> queryFormFields(String code, boolean isform) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        if (isform)
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, Integer.valueOf(1));
        return list((Wrapper) lambdaQueryWrapper);
    }

    public List<OnlCgformField> queryFormFieldsByTableName(String tableName) {
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, tableName));
        if (onlCgformHead != null) {
            LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
            return list((Wrapper) lambdaQueryWrapper);
        }
        return null;
    }

    public OnlCgformField queryFormFieldByTableNameAndField(String tableName, String fieldName) {
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, tableName));
        if (onlCgformHead != null) {
            LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
            lambdaQueryWrapper.eq(OnlCgformField::getDbFieldName, fieldName);
            if (list((Wrapper) lambdaQueryWrapper) != null && list((Wrapper) lambdaQueryWrapper).size() > 0)
                return list((Wrapper) lambdaQueryWrapper).get(0);
        }
        return null;
    }

    public Map<String, Object> queryFormData(List<OnlCgformField> fieldList, String tbname, String id) {
        String str = b.a(tbname, fieldList, id);
        return this.onlCgformFieldMapper.queryFormData(str);
    }

    public Map<String, Object> generateMockData(String tableName) {
        List<OnlCgformField> list = queryFormFieldsByTableName(tableName);
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (list == null || list.size() == 0)
            return (Map) hashMap;
        for (OnlCgformField onlCgformField : list) {
            String str = onlCgformField.getDbFieldName();
            hashMap.put(str, "");
        }
        return (Map) hashMap;
    }

    public List<Map<String, Object>> querySubFormData(List<OnlCgformField> fieldList, String tbname, String linkField, String value) {
        String str = b.a(tbname, fieldList, linkField, value);
        return this.onlCgformFieldMapper.queryListData(str);
    }

    public IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> page, String sql) {
        return ((OnlCgformFieldMapper) this.baseMapper).selectPageBySql(page, sql);
    }

    public List<String> selectOnlineHideColumns(String tbname) {
        String str1 = "online:" + tbname + ":%";
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str2 = loginUser.getId();
        List<String> list = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(str2, str1);
        return a(list);
    }

    public List<OnlCgformField> queryAvailableFields(String cgFormId, String tbname, String taskId, boolean isList) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, cgFormId);
        if (isList) {
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowList, Integer.valueOf(1));
        } else {
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, Integer.valueOf(1));
        }
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = list((Wrapper) lambdaQueryWrapper);
        String str1 = "online:" + tbname + "%";
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str2 = loginUser.getId();
        ArrayList<String> arrayList = new ArrayList<>();
        if (oConvertUtils.isEmpty(taskId)) {
            List list1 = this.onlAuthPageService.queryHideCode(str2, cgFormId, isList);
            if (list1 != null && list1.size() != 0 && list1.get(0) != null)
                arrayList.addAll(list1);
        } else if (oConvertUtils.isNotEmpty(taskId)) {
            List list1 = ((OnlCgformFieldMapper) this.baseMapper).selectFlowAuthColumns(tbname, taskId, "1");
            if (list1 != null && list1.size() > 0 && list1.get(0) != null)
                arrayList.addAll(list1);
        }
        if (arrayList.size() == 0)
            return list;
        ArrayList<OnlCgformField> arrayList1 = new ArrayList<>();
        for (byte b = 0; b < list.size(); b++) {
            OnlCgformField onlCgformField = list.get(b);
            if (b(onlCgformField.getDbFieldName(), arrayList))
                arrayList1.add(onlCgformField);
        }
        return arrayList1;
    }

    public List<String> queryDisabledFields(String tbname) {
        String str1 = "online:" + tbname + "%";
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str2 = loginUser.getId();
        List<String> list = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineDisabledColumns(str2, str1);
        return a(list);
    }

    public List<String> queryDisabledFields(String tbname, String taskId) {
        if (oConvertUtils.isEmpty(taskId))
            return null;
        List<String> list = ((OnlCgformFieldMapper) this.baseMapper).selectFlowAuthColumns(tbname, taskId, "2");
        return a(list);
    }

    private List<String> a(List<String> paramList) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (paramList == null || paramList.size() == 0 || paramList.get(0) == null)
            return arrayList;
        for (String str1 : paramList) {
            if (oConvertUtils.isEmpty(str1))
                continue;
            String str2 = str1.substring(str1.lastIndexOf(":") + 1);
            if (oConvertUtils.isEmpty(str2))
                continue;
            arrayList.add(str2);
        }
        return arrayList;
    }

    public List<OnlCgformField> queryAvailableFields(String tbname, boolean isList, List<OnlCgformField> list, List<String> needList) {
        String str1 = "online:" + tbname + "%";
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str2 = loginUser.getId();
        List<String> list1 = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(str2, str1);
        return a(list1, isList, list, needList);
    }

    public List<OnlCgformField> queryAvailableFields(String cgformId, String tbname, boolean isList, List<OnlCgformField> list, List<String> needList) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str = loginUser.getId();
        List<String> list1 = this.onlAuthPageService.queryListHideColumn(str, cgformId);
        return a(list1, isList, list, needList);
    }

    private List<OnlCgformField> a(List<String> paramList1, boolean paramBoolean, List<OnlCgformField> paramList, List<String> paramList2) {
        ArrayList<OnlCgformField> arrayList = new ArrayList<>();
        boolean bool = true;
        if (paramList1 == null || paramList1.size() == 0 || paramList1.get(0) == null)
            bool = false;
        for (OnlCgformField onlCgformField : paramList) {
            String str = onlCgformField.getDbFieldName();
            if (paramList2 != null && paramList2.contains(str)) {
                onlCgformField.setIsQuery(Integer.valueOf(1));
                arrayList.add(onlCgformField);
                continue;
            }
            if (paramBoolean) {
                if (onlCgformField.getIsShowList().intValue() != 1) {
                    if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable()) && oConvertUtils.isNotEmpty(onlCgformField.getMainField()))
                        arrayList.add(onlCgformField);
                    continue;
                }
            } else if (onlCgformField.getIsShowForm().intValue() != 1) {
                continue;
            }
            if (bool) {
                if (b(str, paramList1))
                    arrayList.add(onlCgformField);
                continue;
            }
            arrayList.add(onlCgformField);
        }
        return arrayList;
    }

    private boolean b(String paramString, List<String> paramList) {
        boolean bool = true;
        for (byte b = 0; b < paramList.size(); b++) {
            String str = paramList.get(b);
            if (!oConvertUtils.isEmpty(str)) {
                String str1 = str.substring(str.lastIndexOf(":") + 1);
                if (!oConvertUtils.isEmpty(str1))
                    if (str1.equals(paramString))
                        bool = false;
            }
        }
        return bool;
    }

    public boolean a(String paramString, List<OnlCgformField> paramList) {
        boolean bool = false;
        for (OnlCgformField onlCgformField : paramList) {
            if (oConvertUtils.camelToUnderline(paramString).equals(onlCgformField.getDbFieldName())) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    public void executeInsertSQL(Map<String, Object> params) {
        ((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(params);
    }

    public void executeUpdatetSQL(Map<String, Object> params) {
        ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(params);
    }

    public List<TreeModel> queryDataListByLinkDown(aEntity linkDown) {
        return ((OnlCgformFieldMapper) this.baseMapper).queryDataListByLinkDown(linkDown);
    }

    public void updateTreeNodeNoChild(String tableName, String filed, String id) {
        Map map = b.a(tableName, filed, id);
        ((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(map);
    }

    public String queryTreeChildIds(OnlCgformHead head, String ids) {
        String str1 = head.getTreeParentIdField();
        String str2 = head.getTableName();
        String[] arrayOfString = ids.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : arrayOfString) {
            if (str != null &&
                    !stringBuffer.toString().contains(str)) {
                if (stringBuffer.toString().length() > 0)
                    stringBuffer.append(",");
                stringBuffer.append(str);
                a(str, str1, str2, stringBuffer);
            }
        }
        return stringBuffer.toString();
    }

    public String queryTreePids(OnlCgformHead head, String ids) {
        String str1 = head.getTreeParentIdField();
        String str2 = head.getTableName();
        StringBuffer stringBuffer = new StringBuffer();
        String[] arrayOfString = ids.split(",");
        for (String str : arrayOfString) {
            if (str != null) {
                String str3 = b.f(str2);
                String str4 = "select * from " + str3 + " where id = '" + str + "'";
                Map map1 = ((OnlCgformFieldMapper) this.baseMapper).queryFormData(str4);
                Map map2 = b.a(map1);
                String str5 = map2.get(str1).toString();
                String str6 = "'" + String.join("','", (CharSequence[]) arrayOfString) + "'";
                String str7 = "select * from " + b.f(str2) + " where " + str1 + "= '" + str5 + "' and id not in(" + str6 + ")";
                List list = this.onlCgformFieldMapper.queryListBySql(str7);
                if ((list == null || list.size() == 0) && !Arrays.<String>asList(arrayOfString).contains(str5) &&
                        !stringBuffer.toString().contains(str5))
                    stringBuffer.append(str5).append(",");
            }
        }
        return stringBuffer.toString();
    }

    public String queryForeignKey(String cgFormId, String mainTable) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, cgFormId);
        lambdaQueryWrapper.eq(OnlCgformField::getMainTable, mainTable);
        List<OnlCgformField> list = list((Wrapper) lambdaQueryWrapper);
        if (list != null && list.size() > 0)
            return ((OnlCgformField) list.get(0)).getMainField();
        return null;
    }

    public List<Map<String, Object>> queryListBySql(String sql) {
        return this.onlCgformFieldMapper.queryListBySql(sql);
    }

    public IPage<Map<String, Object>> selectPageBySql(String sql, int pageNo, int pageSize) {
        Page page = new Page(pageNo, pageSize);
        return this.onlCgformFieldMapper.selectPageBySql(page, sql);
    }

    private StringBuffer a(String paramString1, String paramString2, String paramString3, StringBuffer paramStringBuffer) {
        String str = "select * from " + b.f(paramString3) + " where " + paramString2 + "= '" + paramString1 + "'";
        List list = this.onlCgformFieldMapper.queryListBySql(str);
        if (list != null && list.size() > 0)
            for (Map map1 : list) {
                Map map2 = b.a(map1);
                if (!paramStringBuffer.toString().contains(map2.get("id").toString()))
                    paramStringBuffer.append(",").append(map2.get("id"));
                a(map2.get("id").toString(), paramString2, paramString3, paramStringBuffer);
            }
        return paramStringBuffer;
    }

    private String a(List<OnlCgformField> paramList, Map<String, Object> paramMap) {
        Object object = paramMap.get("column");
        ArrayList<h> arrayList = new ArrayList<>();
        if (object != null && !"id".equals(object.toString())) {
            String str1 = object.toString();
            Object object1 = paramMap.get("order");
            String str2 = "desc";
            if (object1 != null)
                str2 = object1.toString();
            h h = new h(str1, str2);
            arrayList.add(h);
        } else {
            for (OnlCgformField onlCgformField : paramList) {
                if ("1".equals(onlCgformField.getSortFlag())) {
                    String str = onlCgformField.getFieldExtendJson();
                    h h = new h(onlCgformField.getDbFieldName());
                    if (str != null && !"".equals(str)) {
                        JSONObject jSONObject = JSON.parseObject(str);
                        String str1 = jSONObject.getString("orderRule");
                        if (str1 != null && !"".equals(str1)) {
                            h.setRule(str1);
                            arrayList.add(h);
                        }
                    }
                }
            }
            if (arrayList.size() == 0) {
                h h = h.a();
                arrayList.add(h);
            }
        }
        ArrayList<String> arrayList1 = new ArrayList<>();
        for (h h : arrayList) {
            if (a(h.getColumn(), paramList)) {
                String str = h.getRealSql();
                arrayList1.add(str);
            }
        }
        return " ORDER BY " + String.join(",", (Iterable) arrayList1);
    }

    private int a(OnlCgformHead paramOnlCgformHead, List<Map<String, String>> paramList, int paramInt, boolean paramBoolean) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, paramOnlCgformHead.getId());
        lambdaQueryWrapper.eq(OnlCgformField::getIsQuery, Integer.valueOf(1));
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, b.b);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list = list((Wrapper) lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            hashMap.put("label", onlCgformField.getDbFieldTxt());
            if (paramBoolean) {
                hashMap.put("field", paramOnlCgformHead.getTableName() + "@" + onlCgformField.getDbFieldName());
            } else {
                hashMap.put("field", onlCgformField.getDbFieldName());
            }
            hashMap.put("dbField", onlCgformField.getDbFieldName());
            hashMap.put("mode", onlCgformField.getQueryMode());
            if (oConvertUtils.isNotEmpty(onlCgformField.getFieldExtendJson()))
                hashMap.put("fieldExtendJson", onlCgformField.getFieldExtendJson());
            String str = onlCgformField.getQueryConfigFlag();
            if ("1".equals(str)) {
                String str1 = onlCgformField.getQueryShowType();
                hashMap.put("config", "1");
                hashMap.put("view", str1);
                hashMap.put("defValue", onlCgformField.getQueryDefVal());
                if ("cat_tree".equals(str1)) {
                    hashMap.put("pcode", onlCgformField.getQueryDictField());
                } else if ("sel_tree".equals(str1)) {
                    String[] arrayOfString = onlCgformField.getQueryDictText().split(",");
                    String str2 = onlCgformField.getQueryDictTable() + "," + arrayOfString[2] + "," + arrayOfString[0];
                    hashMap.put("dict", str2);
                    hashMap.put("pidField", arrayOfString[1]);
                    hashMap.put("hasChildField", arrayOfString[3]);
                    hashMap.put("pidValue", onlCgformField.getQueryDictField());
                } else {
                    hashMap.put("dictTable", onlCgformField.getQueryDictTable());
                    hashMap.put("dictCode", onlCgformField.getQueryDictField());
                    hashMap.put("dictText", onlCgformField.getQueryDictText());
                }
            } else {
                String str1 = onlCgformField.getFieldShowType();
                hashMap.put("view", str1);
                hashMap.put("mode", onlCgformField.getQueryMode());
                if ("cat_tree".equals(str1)) {
                    hashMap.put("pcode", onlCgformField.getDictField());
                } else if ("sel_tree".equals(str1)) {
                    String[] arrayOfString = onlCgformField.getDictText().split(",");
                    String str2 = onlCgformField.getDictTable() + "," + arrayOfString[2] + "," + arrayOfString[0];
                    hashMap.put("dict", str2);
                    hashMap.put("pidField", arrayOfString[1]);
                    hashMap.put("hasChildField", arrayOfString[3]);
                    hashMap.put("pidValue", onlCgformField.getDictField());
                } else {
                    hashMap.put("dictTable", onlCgformField.getDictTable());
                    hashMap.put("dictCode", onlCgformField.getDictField());
                    hashMap.put("dictText", onlCgformField.getDictText());
                }
            }
            if (++paramInt > 2)
                hashMap.put("hidden", "1");
            paramList.add(hashMap);
        }
        return paramInt;
    }

    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public void clearCacheOnlineConfig() {
    }

    public e getQueryInfo(OnlCgformHead head, Map<String, Object> params, List<String> needList) {
        String str1 = head.getTableName();
        String str2 = head.getId();
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str2);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, b.b);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list1 = list((Wrapper) lambdaQueryWrapper);
        List<OnlCgformField> list2 = new ArrayList<>();
        List<String> list = head.getSelectFieldList();
        if (list != null && list.size() > 0) {
            List<OnlCgformField> list5 = a(str2, list1, list, needList);
        } else {
            list2 = queryAvailableFields(str2, str1, true, list1, needList);
        }
        StringBuffer stringBuffer = new StringBuffer();
        b.a(str1, list2, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str3 = loginUser.getId();
        List list3 = this.onlAuthDataService.queryUserOnlineAuthData(str3, str2);
        if (list3 != null && list3.size() > 0)
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        bLinkConstant b = new bLinkConstant("t.");
        b.setTableName(str1);
        b.setNeedList(needList);
        b.setSubTableStr(head.getSubTableStr());
        List list4 = b.g(list1);
        String str4 = b.a(list4, params, list3);
        Map map = b.getSqlParams();
        if (str4.trim().length() > 0)
            stringBuffer.append(" t ").append(" where  ").append(str4);
        String str5 = a(list1, params);
        stringBuffer.append(str5);
        e e = new e(stringBuffer.toString(), map);
        e.setFieldList(list2);
        return e;
    }

    public void addOnlineInsertDataLog(String tableName, String dataId) {
        a.execute(() -> {
            String str = " 创建了记录";
            DataLogDTO dataLogDTO = new DataLogDTO(paramString1, paramString2, str, "comment");
            this.sysBaseAPI.saveDataLog(dataLogDTO);
        });
    }

    public void addOnlineUpdateDataLog(String tableName, String dataId, List<OnlCgformField> fieldList, JSONObject json) {
        String str = b.f(tableName);
        Map<String, Object> map = a(str, dataId);
        if (map != null)
            a.execute(() -> {
                DataLogDTO dataLogDTO = new DataLogDTO(paramString1, paramString2, "comment");
                String str = a(paramList, paramJSONObject, paramMap);
                dataLogDTO.setContent(str);
                this.sysBaseAPI.saveDataLog(dataLogDTO);
            });
    }

    private String a(List<OnlCgformField> paramList, JSONObject paramJSONObject, Map<String, Object> paramMap) {
        StringBuffer stringBuffer = new StringBuffer();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (OnlCgformField onlCgformField : paramList) {
            String str1 = onlCgformField.getDbFieldName();
            if (null == str1)
                continue;
            if ("id".equalsIgnoreCase(str1) || "SYS_ORG_CODE".equalsIgnoreCase(str1) || "CREATE_BY"
                    .equalsIgnoreCase(str1) || "UPDATE_BY".equalsIgnoreCase(str1) || "CREATE_TIME"
                    .equalsIgnoreCase(str1) || "UPDATE_TIME".equalsIgnoreCase(str1))
                continue;
            String str2 = onlCgformField.getDbType();
            if ("blob".equalsIgnoreCase(str2) || "text".equalsIgnoreCase(str2))
                continue;
            if (oConvertUtils.isEmpty(paramJSONObject.get(str1)) && oConvertUtils.isEmpty(paramMap.get(str1)))
                continue;
            String str3 = "空";
            String str4 = "";
            if (paramJSONObject.get(str1) == null) {
                str4 = "空";
            } else {
                String str = paramJSONObject.get(str1).toString();
                if (str.length() == 0) {
                    str4 = "空";
                } else {
                    str4 = str;
                }
            }
            if ("Datetime".equalsIgnoreCase(str2)) {
                if (paramMap.get(str1) != null) {
                    LocalDateTime localDateTime = (LocalDateTime) paramMap.get(str1);
                    str3 = dateTimeFormatter.format(localDateTime);
                }
            } else if ("BigDecimal".equalsIgnoreCase(str2) || "double".equalsIgnoreCase(str2)) {
                Integer integer = onlCgformField.getDbPointLength();
                if (paramMap.get(str1) != null)
                    str3 = paramMap.get(str1).toString();
                if (paramJSONObject.get(str1) == null || paramJSONObject.get(str1).toString().length() == 0) {
                    str4 = "空";
                } else {
                    String str = paramJSONObject.get(str1).toString();
                    if (str.indexOf(".") < 0)
                        str = str + ".";
                    if (str.length() - 1 - str.indexOf(".") < integer.intValue()) {
                        int i = 0;
                        do {
                            str = str + "0";
                            i = str.length() - 1 - str.indexOf(".");
                        } while (i < integer.intValue());
                    }
                    str4 = str;
                }
            } else if (paramMap.get(str1) != null) {
                str3 = paramMap.get(str1).toString();
                if (str3.length() == 0)
                    str3 = "空";
            }
            if (str3.equals(str4))
                continue;
            if ("double".equalsIgnoreCase(str2) &&
                    paramMap.get(str1) != null && paramJSONObject.get(str1) != null) {
                BigDecimal bigDecimal1 = new BigDecimal(paramMap.get(str1).toString());
                BigDecimal bigDecimal2 = new BigDecimal(paramJSONObject.get(str1).toString());
                if (bigDecimal1.compareTo(bigDecimal2) == 0)
                    continue;
            }
            stringBuffer.append("  将名称为【" + onlCgformField.getDbFieldTxt() + "】的字段内容 " + str3 + " 修改为 " + str4 + "；  ");
        }
        return stringBuffer.toString();
    }

    private Map<String, Object> a(String paramString1, String paramString2) {
        String str = "select * from " + paramString1 + " where id = '" + paramString2 + "'";
        return ((OnlCgformFieldMapper) this.baseMapper).queryFormData(str);
    }

    @Cacheable(value = {"sys:cache:online:linkTable"}, key = "#table+#textString+#code")
    public List<Map<String, Object>> queryLinkTableDictList(String table, String textString, String code) {
        String str = "select " + textString + ", " + code + " from " + table;
        return ((OnlCgformFieldMapper) this.baseMapper).queryListBySql(str);
    }

    public void handleLinkTableDictData(String headId, List<Map<String, Object>> dataList) {
        if (dataList == null || dataList.size() == 0)
            return;
        String[] arrayOfString = {"link_table_field", "link_table"};
        LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper) ((LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, headId)).in(OnlCgformField::getFieldShowType, (Object[]) arrayOfString);
        List list = this.onlCgformFieldMapper.selectList((Wrapper) lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            for (OnlCgformField onlCgformField : list) {
                if ("link_table_field".equals(onlCgformField.getFieldShowType())) {
                    String str1 = onlCgformField.getDictTable();
                    List<String> list1 = (List) hashMap.get(str1);
                    if (list1 == null)
                        list1 = new ArrayList<>();
                    String str2 = onlCgformField.getDbFieldName() + "," + onlCgformField.getDictText();
                    list1.add(str2);
                    hashMap.put(str1, list1);
                }
            }
            for (OnlCgformField onlCgformField : list) {
                if ("link_table".equals(onlCgformField.getFieldShowType())) {
                    String str1 = onlCgformField.getDictTable();
                    String str2 = onlCgformField.getDictText();
                    String str3 = onlCgformField.getDictField();
                    List<OnlCgformField> list1 = a(str1, str2, str3);
                    Map<String, List<DictModel>> map = b(list1);
                    List<Map<String, Object>> list2 = queryLinkTableDictList(str1, str2, str3);
                    String str4 = onlCgformField.getDbFieldName().toLowerCase();
                    String str5 = str2.split(",")[0];
                    String str6 = str3;
                    for (Map<String, String> map1 : dataList) {
                        Object object = map1.get(str4);
                        if (object == null || "".equals(object.toString()))
                            continue;
                        String str = a(map, list2, str5, str6, object);
                        map1.put(str4 + "_dictText", str);
                        List list3 = (List) hashMap.get(str4);
                        if (list3 != null && list3.size() > 0)
                            for (String str7 : list3) {
                                String[] arrayOfString1 = str7.split(",");
                                String str8 = arrayOfString1[0];
                                String str9 = arrayOfString1[1];
                                String str10 = a(map, list2, str9, str6, object);
                                map1.put(str8.toLowerCase(), str10);
                            }
                    }
                }
            }
        }
    }

    private List<OnlCgformField> a(String paramString1, String paramString2, String paramString3) {
        LambdaQueryWrapper lambdaQueryWrapper1 = (LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, paramString1);
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne((Wrapper) lambdaQueryWrapper1);
        if (onlCgformHead == null)
            throw new JeecgBootException("实体未找到");
        if (oConvertUtils.isEmpty(paramString2) || oConvertUtils.isEmpty(paramString3))
            throw new JeecgBootException("关联记录字典参数不正确");
        String[] arrayOfString = paramString2.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(paramString3);
        for (String str : arrayOfString)
            arrayList.add(str);
        LambdaQueryWrapper lambdaQueryWrapper2 = (LambdaQueryWrapper) ((LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId())).in(OnlCgformField::getDbFieldName, arrayList);
        return this.onlCgformFieldMapper.selectList((Wrapper) lambdaQueryWrapper2);
    }

    private Map<String, List<DictModel>> b(List<OnlCgformField> paramList) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (OnlCgformField onlCgformField : paramList) {
            String str1 = onlCgformField.getDictTable();
            String str2 = onlCgformField.getDictText();
            String str3 = onlCgformField.getDictField();
            if (b.c(onlCgformField.getFieldShowType())) {
                if (oConvertUtils.isNotEmpty(str1) && oConvertUtils.isNotEmpty(str2) && oConvertUtils.isNotEmpty(str3)) {
                    List list = this.sysBaseAPI.queryTableDictItemsByCode(str1, str2, str3);
                    hashMap.put(onlCgformField.getDbFieldName(), list);
                    continue;
                }
                if (oConvertUtils.isNotEmpty(str3)) {
                    List list = this.sysBaseAPI.queryDictItemsByCode(str3);
                    hashMap.put(onlCgformField.getDbFieldName(), list);
                }
            }
        }
        return (Map) hashMap;
    }

    private List<OnlCgformField> a(String paramString, List<OnlCgformField> paramList, List<String> paramList1, List<String> paramList2) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str = loginUser.getId();
        ArrayList<OnlCgformField> arrayList = new ArrayList<>();
        for (OnlCgformField onlCgformField : paramList) {
            String str1 = onlCgformField.getDbFieldName();
            if (paramList1.indexOf(str1) >= 0)
                arrayList.add(onlCgformField);
        }
        List<String> list = this.onlAuthPageService.queryListHideColumn(str, paramString);
        return a(list, true, arrayList, paramList2);
    }

    private String a(Map<String, List<DictModel>> paramMap, List<Map<String, Object>> paramList, String paramString1, String paramString2, Object paramObject) {
        String str = paramObject.toString();
        ArrayList<String> arrayList = new ArrayList<>();
        String[] arrayOfString = str.split(",");
        for (String str1 : arrayOfString) {
            String str2 = "";
            for (Map<String, Object> map : paramList) {
                if (str1.equals(map.get(paramString2)) &&
                        map.get(paramString1) != null) {
                    str2 = map.get(paramString1).toString();
                    List list = paramMap.get(paramString1);
                    if (list != null && list.size() > 0)
                        for (DictModel dictModel : list) {
                            if (dictModel.getValue().equals(str2))
                                str2 = dictModel.getText();
                        }
                }
            }
            if (oConvertUtils.isNotEmpty(str2))
                arrayList.add(str2);
        }
        if (arrayList.size() > 0)
            return String.join(",", (Iterable) arrayList);
        return "";
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\a\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
