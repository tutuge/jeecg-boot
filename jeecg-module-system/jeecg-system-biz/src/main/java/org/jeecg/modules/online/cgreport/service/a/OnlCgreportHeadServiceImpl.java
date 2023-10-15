package org.jeecg.modules.online.cgreport.service.a;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.DynamicDataSourceModel;
import org.jeecg.common.util.dynamic.db.DataSourceCachePool;
import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.enums.cEnum;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportHeadMapper;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.config.bAttribute.eTableConfig;
import org.jeecg.modules.online.config.dUtil.bAlias;
import org.jeecg.modules.online.config.dUtil.eDbTableHandle;
import org.jeecg.modules.online.config.exception.AException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("onlCgreportHeadServiceImpl")
public class OnlCgreportHeadServiceImpl extends ServiceImpl<OnlCgreportHeadMapper, OnlCgreportHead> implements IOnlCgreportHeadService {
    private static final Logger a = LoggerFactory.getLogger(OnlCgreportHeadServiceImpl.class);

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

    @Autowired
    private OnlCgreportHeadMapper mapper;

    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseAPI;

    public Map<String, Object> executeSelectSql(String sql, String onlCgreportHeadId, Map<String, Object> params) throws SQLException {
        List<OnlCgreportItem> list2;
        IPage iPage;
        String str1 = null;
        try {
            str1 = eDbTableHandle.getDatabaseType();
        } catch (AException a) {
            a.printStackTrace();
        }
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper1 = Wrappers.lambdaQuery(OnlCgreportParam.class)
                .eq(OnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        List<OnlCgreportParam> list1 = this.onlCgreportParamService.list(lambdaQueryWrapper1);
        if (list1 != null && !list1.isEmpty())
            for (OnlCgreportParam onlCgreportParam : list1) {
                Object object1 = params.get("self_" + onlCgreportParam.getParamName());
                Object object2 = params.get(onlCgreportParam.getParamName());
                String str5 = "";
                if (object1 != null) {
                    str5 = object1.toString();
                } else if (object1 == null && oConvertUtils.isNotEmpty(object2)) {
                    str5 = object2.toString();
                } else if (object1 == null && oConvertUtils.isNotEmpty(onlCgreportParam.getParamValue())) {
                    str5 = onlCgreportParam.getParamValue();
                }
                String str6 = "${" + onlCgreportParam.getParamName() + "}";
                if (sql.indexOf(str6) > 0) {
                    if (str5 != null && str5.startsWith("'") && str5.endsWith("'"))
                        str5 = str5.substring(1, str5.length() - 1);
                    sql = sql.replace(str6, str5);
                    continue;
                }
                if (oConvertUtils.isNotEmpty(str5))
                    params.put("popup_param_pre__" + onlCgreportParam.getParamName(), str5);
            }
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Integer integer1 = oConvertUtils.getInt(params.get("pageSize"), 10);
        Integer integer2 = oConvertUtils.getInt(params.get("pageNo"), 1);
        Page page = new Page(integer2, integer1);
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper2 = Wrappers.lambdaQuery(OnlCgreportItem.class)
                .eq(OnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : (String[]) params.keySet().toArray((Object[]) new String[0])) {
            if (str.startsWith("force_")) {
                String str5 = str.substring("force_".length());
                arrayList.add(str5);
                params.put(str5, params.get(str));
            }
        }
        if (arrayList.size() > 0) {
            lambdaQueryWrapper2.in(OnlCgreportItem::getFieldName, arrayList);
            list2 = this.onlCgreportItemService.list(lambdaQueryWrapper2);
            if (list2.size() < arrayList.size()) {
                boolean bool1 = arrayList.stream().anyMatch("id"::equalsIgnoreCase);
                boolean bool2 = list2.stream().anyMatch(paramOnlCgreportItem ->
                        "id".equalsIgnoreCase(paramOnlCgreportItem.getFieldName()));
                if (bool1 && !bool2) {
                    OnlCgreportItem onlCgreportItem = new OnlCgreportItem();
                    onlCgreportItem.setFieldName("id");
                    onlCgreportItem.setFieldType("String");
                    onlCgreportItem.setSearchMode("single");
                    onlCgreportItem.setIsSearch(Integer.valueOf(1));
                    list2.add(onlCgreportItem);
                }
            } else {
                list2.forEach(paramOnlCgreportItem -> paramOnlCgreportItem.setIsSearch(Integer.valueOf(1)));
            }
        } else {
            lambdaQueryWrapper2.eq(OnlCgreportItem::getIsSearch, Integer.valueOf(1));
            list2 = this.onlCgreportItemService.list((Wrapper) lambdaQueryWrapper2);
        }
        sql = QueryGenerator.convertSystemVariables(sql);
        ArrayList<eTableConfig> arrayList1 = new ArrayList<>();
        for (OnlCgreportItem onlCgreportItem : list2)
            arrayList1.add(new eTableConfig(onlCgreportItem));
        String str2 = "jeecg_rp_temp.";
        bAlias b = new bAlias(str2, str1);
        String str3 = b.a(arrayList1, params);
        Map map = b.getSqlParams();
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) &&
                "SQLSERVER".equalsIgnoreCase(str1))
            throw new JeecgBootException("SqlServer不支持SQL内排序!");
        String str4 = "select * from (" + sql + ") jeecg_rp_temp ";
        if (str3.trim().length() > 0)
            str4 = str4 + " where " + str3;
        Object object = params.get("column");
        if (object != null) {
            String str5 = String.valueOf(params.get("order"));
            String[] arrayOfString = String.valueOf(object).split(",");
            String str6 = String.join(" " + str5 + ", jeecg_rp_temp.", (CharSequence[]) arrayOfString);
            str4 = str4 + " order by jeecg_rp_temp." + str6 + " " + str5;
        }
        if (Boolean.valueOf(String.valueOf(params.get("getAll"))).booleanValue()) {
            List list = this.mapper.selectByCondition(str4, map);
            Page page1 = new Page();
            page1.setRecords(list);
            page1.setTotal(list.size());
        } else {
            iPage = this.mapper.selectPageByCondition(page, str4, map);
        }
        hashMap.put("total", Long.valueOf(iPage.getTotal()));
        hashMap.put("records", b.d(iPage.getRecords()));
        return (Map) hashMap;
    }

    public Map<String, Object> executeSelectSqlDynamic(String dbKey, String sql, Map<String, Object> params, String onlCgreportHeadId) {
        String str1 = (String) params.get("order");
        String str2 = (String) params.get("column");
        int i = oConvertUtils.getInt(params.get("pageNo"), 1);
        int j = oConvertUtils.getInt(params.get("pageSize"), 10);
        DynamicDataSourceModel dynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(dbKey);
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) &&
                "3".equalsIgnoreCase(dynamicDataSourceModel.getDbType()))
            throw new JeecgBootException("SqlServer不支持SQL内排序!");
        LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
        lambdaQueryWrapper1.eq(OnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        List list1 = this.onlCgreportParamService.list((Wrapper) lambdaQueryWrapper1);
        if (list1 != null && list1.size() > 0)
            for (OnlCgreportParam onlCgreportParam : list1) {
                Object object1 = params.get("self_" + onlCgreportParam.getParamName());
                String str = "";
                if (object1 != null) {
                    str = object1.toString();
                } else if (object1 == null && oConvertUtils.isNotEmpty(onlCgreportParam.getParamValue())) {
                    str = onlCgreportParam.getParamValue();
                }
                sql = sql.replace("${" + onlCgreportParam.getParamName() + "}", str);
            }
        LambdaQueryWrapper lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        lambdaQueryWrapper2.eq(OnlCgreportItem::getIsSearch, Integer.valueOf(1));
        List list2 = this.onlCgreportItemService.list((Wrapper) lambdaQueryWrapper2);
        sql = QueryGenerator.convertSystemVariables(sql);
        ArrayList<eTableConfig> arrayList = new ArrayList<>();
        for (OnlCgreportItem onlCgreportItem : list2)
            arrayList.add(new eTableConfig(onlCgreportItem));
        String str3 = "jeecg_rp_temp.";
        String str4 = cEnum.b(dynamicDataSourceModel.getDbType());
        bConstant b = new bConstant(str3, str4);
        b.setDaoType("jdbcTemplate");
        String str5 = b.getId(arrayList, params);
        Map map1 = b.getSqlParams();
        String str6 = "select * from (" + sql + ") jeecg_rp_temp ";
        if (str5.trim().length() > 0)
            str6 = str6 + " where " + str5;
        String str7 = org.jeecg.modules.online.cgreport.c.c.c(str6);
        Object object = params.get("column");
        if (object != null)
            str6 = str6 + " order by jeecg_rp_temp." + object.toString() + " " + params.get("order").toString();
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Map map2 = DynamicDBUtil.queryCount(dbKey, str7, map1);
        hashMap.put("total", map2.get("total"));
        List list3 = org.jeecg.modules.online.cgreport.c.c.a(String.valueOf(params.get("getAll")), dbKey, str6, i, j, map1);
        hashMap.put("records", b.d(list3));
        return (Map) hashMap;
    }

    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = {"sys:cache:online:rp"}, allEntries = true, beforeInvocation = true)
    public Result<?> editAll(OnlCgreportModel values) {
        OnlCgreportHead onlCgreportHead1 = values.getHead();
        OnlCgreportHead onlCgreportHead2 = (OnlCgreportHead) getById(onlCgreportHead1.getId());
        if (onlCgreportHead2 == null)
            return Result.error("未找到对应实体");
        updateById(onlCgreportHead1);
        LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
        lambdaQueryWrapper1.eq(OnlCgreportItem::getCgrheadId, onlCgreportHead1.getId());
        this.onlCgreportItemService.remove((Wrapper) lambdaQueryWrapper1);
        LambdaQueryWrapper lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, onlCgreportHead1.getId());
        this.onlCgreportParamService.remove((Wrapper) lambdaQueryWrapper2);
        for (OnlCgreportParam onlCgreportParam : values.getParams())
            onlCgreportParam.setCgrheadId(onlCgreportHead1.getId());
        for (OnlCgreportItem onlCgreportItem : values.getItems()) {
            onlCgreportItem.setFieldName(onlCgreportItem.getFieldName().trim().toLowerCase());
            onlCgreportItem.setCgrheadId(onlCgreportHead1.getId());
        }
        this.onlCgreportItemService.saveBatch(values.getItems());
        this.onlCgreportParamService.saveBatch(values.getParams());
        return Result.ok("全部修改成功");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Result<?> delete(String id) {
        boolean bool = removeById(id);
        if (bool) {
            LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
            lambdaQueryWrapper1.eq(OnlCgreportItem::getCgrheadId, id);
            this.onlCgreportItemService.remove((Wrapper) lambdaQueryWrapper1);
            LambdaQueryWrapper lambdaQueryWrapper2 = new LambdaQueryWrapper();
            lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, id);
            this.onlCgreportParamService.remove((Wrapper) lambdaQueryWrapper2);
        }
        return Result.ok("删除成功");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Result<?> bathDelete(String[] ids) {
        for (String str : ids) {
            boolean bool = removeById(str);
            if (bool) {
                LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
                lambdaQueryWrapper1.eq(OnlCgreportItem::getCgrheadId, str);
                this.onlCgreportItemService.remove((Wrapper) lambdaQueryWrapper1);
                LambdaQueryWrapper lambdaQueryWrapper2 = new LambdaQueryWrapper();
                lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, str);
                this.onlCgreportParamService.remove((Wrapper) lambdaQueryWrapper2);
            }
        }
        return Result.ok("删除成功");
    }

    public List<String> getSqlFields(String sql, String dbKey) throws SQLException, AException {
        List<String> list = null;
        if (StringUtils.isNotBlank(dbKey)) {
            list = a(sql, dbKey);
        } else {
            list = a(sql, (String) null);
        }
        return list;
    }

    public List<String> getSqlParams(String sql) {
        if (oConvertUtils.isEmpty(sql))
            return null;
        ArrayList<String> arrayList = new ArrayList<>();
        String str = "\\$\\{\\w+\\}";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String str1 = matcher.group();
            arrayList.add(str1.substring(str1.indexOf("{") + 1, str1.indexOf("}")));
        }
        return arrayList;
    }

    private List<String> a(String paramString1, String paramString2) throws SQLException, AException {
        if (oConvertUtils.isEmpty(paramString1))
            return null;
        paramString1 = paramString1.replace("[^><]=", " = ");
        paramString1 = paramString1.trim();
        if (paramString1.endsWith(";"))
            paramString1 = paramString1.substring(0, paramString1.length() - 1);
        paramString1 = QueryGenerator.convertSystemVariables(paramString1);
        paramString1 = org.jeecg.modules.online.cgreport.c.c.a(paramString1);
        Set<? extends String> set = null;
        if (StringUtils.isNotBlank(paramString2)) {
            DynamicDataSourceModel dynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(paramString2);
            if (ReUtil.contains(" order\\s+by ", paramString1.toLowerCase()) &&
                    "3".equalsIgnoreCase(dynamicDataSourceModel.getDbType()))
                throw new JeecgBootException("SqlServer不支持SQL内排序!");
            Map map = org.jeecg.modules.online.cgreport.c.c.a(paramString2, paramString1);
            if (map == null) {
                if (!paramString1.contains("*"))
                    try {
                        map = bConstant.getId(paramString1);
                    } catch (Exception exception) {
                    }
                if (map == null)
                    throw new JeecgBootException("该报表sql没有数据");
            }
            set = map.keySet();
        } else {
            String str = eTableConfig.getDatabaseType();
            if (ReUtil.contains(" order\\s+by ", paramString1.toLowerCase()) &&
                    "SQLSERVER".equalsIgnoreCase(str))
                throw new JeecgBootException("SqlServer不支持SQL内排序!");
            IPage iPage = this.mapper.selectPageBySql(new Page(1L, 1L), paramString1);
            List<Map> list = iPage.getRecords();
            if (list.size() < 1) {
                if (!paramString1.contains("*"))
                    try {
                        set = bConstant.getId(paramString1).keySet();
                    } catch (Exception exception) {
                    }
                if (set == null)
                    throw new JeecgBootException("该报表sql没有数据");
            } else {
                set = ((Map) list.get(0)).keySet();
            }
        }
        if (set != null)
            set.remove("ROW_ID");
        return new ArrayList<>(set);
    }

    public Map<String, Object> queryCgReportConfig(String reportId) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Map map = this.mapper.queryCgReportMainConfig(reportId);
        List list1 = this.mapper.queryCgReportItems(reportId);
        List list2 = this.mapper.queryCgReportParams(reportId);
        if (eTableConfig.a()) {
            hashMap.put("main", bConstant.getId(map));
            hashMap.put("items", bConstant.d(list1));
        } else {
            hashMap.put("main", map);
            hashMap.put("items", list1);
        }
        hashMap.put("params", list2);
        return (Map) hashMap;
    }

    @Deprecated
    public List<DictModel> queryDictSelectData(String sql, String keyword) {
        List<DictModel> list = new ArrayList<>();
        Page page = new Page();
        page.setSearchCount(false);
        page.setCurrent(1L);
        page.setSize(10L);
        sql = sql.trim();
        int i = sql.lastIndexOf(";");
        if (i == sql.length() - 1)
            sql = sql.substring(0, i);
        if (keyword != null && !"".equals(keyword)) {
            String str = " like '%" + keyword + "%'";
            sql = "select * from (" + sql + ") t where t.value " + str + " or " + "t.text" + str;
        }
        IPage iPage = ((OnlCgreportHeadMapper) this.baseMapper).selectPageBySql(page, sql);
        List list1 = iPage.getRecords();
        list1 = (List) list1.stream().filter(paramMap -> (paramMap != null)).collect(Collectors.toList());
        if (list1 != null && list1.size() != 0) {
            String str = JSON.toJSONString(list1);
            list = JSON.parseArray(str, DictModel.class);
        }
        return list;
    }

    @Cacheable(value = {"sys:cache:online:rp"}, key = "'column-'+#code+'-'+#queryDict")
    public Map<String, Object> queryColumnInfo(String code, boolean queryDict) {
        HashMap<Object, Object> hashMap1 = new HashMap<>(5);
        LambdaQueryWrapper<OnlCgreportItem> queryWrapper = Wrappers.lambdaQuery(OnlCgreportItem.class)
                .eq(OnlCgreportItem::getCgrheadId, code)
                .eq(OnlCgreportItem::getIsShow, Integer.valueOf(1))
                .orderByAsc(OnlCgreportItem::getOrderNum);
        List<OnlCgreportItem> list = this.onlCgreportItemService.list( queryWrapper);
        JSONArray jSONArray1 = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        HashMap<Object, Object> hashMap2 = new HashMap<>(5);
        boolean bool = false;
        for (OnlCgreportItem onlCgreportItem : list) {
            JSONObject jSONObject = new JSONObject(4);
            jSONObject.put("title", onlCgreportItem.getFieldTxt());
            jSONObject.put("dataIndex", onlCgreportItem.getFieldName());
            jSONObject.put("fieldType", onlCgreportItem.getFieldType());
            jSONObject.put("align", "center");
            jSONObject.put("sorter", "true");
            jSONObject.put("isTotal", onlCgreportItem.getIsTotal());
            jSONObject.put("groupTitle", onlCgreportItem.getGroupTitle());
            if (oConvertUtils.isNotEmpty(onlCgreportItem.getGroupTitle()))
                bool = true;
            String str1 = onlCgreportItem.getFieldType();
            if ("Integer".equals(str1) || "Date".equals(str1) || "Long".equals(str1))
                jSONObject.put("sorter", "true");
            if (StringUtils.isNotBlank(onlCgreportItem.getFieldHref())) {
                String str = "fieldHref_" + onlCgreportItem.getFieldName();
                JSONObject jSONObject1 = new JSONObject();
                jSONObject1.put("customRender", str);
                jSONObject.put("scopedSlots", jSONObject1);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("slotName", str);
                jSONObject2.put("href", onlCgreportItem.getFieldHref());
                jSONArray1.add(jSONObject2);
            }
            String str2 = onlCgreportItem.getDictCode();
            if (str2 != null && !"".equals(str2))
                if (queryDict) {
                    List<DictModel> list1 = queryColumnDict(onlCgreportItem.getDictCode(), null, null);
                    hashMap2.put(onlCgreportItem.getFieldName(), list1);
                    jSONObject.put("customRender", onlCgreportItem.getFieldName());
                } else {
                    jSONObject.put("dictCode", str2);
                }
            jSONArray2.add(jSONObject);
        }
        if (queryDict){
            hashMap1.put("dictOptions", hashMap2);
        }
        hashMap1.put("columns", jSONArray2);
        hashMap1.put("fieldHrefSlots", jSONArray1);
        hashMap1.put("isGroupTitle", bool);
        return (Map) hashMap1;
    }

    public List<DictModel> queryColumnDict(String dictCode, JSONArray records, String fieldName) {
        List<DictModel> list = null;
        if (oConvertUtils.isNotEmpty(dictCode))
            if (dictCode.trim().toLowerCase().indexOf("select ") == 0 && (fieldName == null || records.size() > 0)) {
                dictCode = dictCode.trim();
                int i = dictCode.lastIndexOf(";");
                if (i == dictCode.length() - 1)
                    dictCode = dictCode.substring(0, i);
                String str = "SELECT * FROM (" + dictCode + ") temp ";
                if (records != null) {
                    HashSet<String> hashSet = new HashSet();
                    for (byte b = 0; b < records.size(); b++) {
                        JSONObject jSONObject = records.getJSONObject(b);
                        String str2 = jSONObject.getString(fieldName);
                        if (StringUtils.isNotBlank(str2))
                            hashSet.add(str2);
                    }
                    String str1 = "'" + StringUtils.join(hashSet, "','") + "'";
                    str = str + "WHERE temp.value IN (" + str1 + ")";
                }
                List list1 = ((OnlCgreportHeadMapper) getBaseMapper()).executeSelete(str);
                if (list1 != null && list1.size() != 0) {
                    String str1 = JSON.toJSONString(list1);
                    list = JSON.parseArray(str1, DictModel.class);
                }
            } else {
                list = this.sysBaseAPI.queryDictItemsByCode(dictCode);
            }
        return list;
    }

    public List<DictModel> queryColumnDictList(String dictCode, List<Map<String, Object>> records, String fieldName) {
        List<DictModel> list = null;
        if (oConvertUtils.isNotEmpty(dictCode)) {
            dictCode = dictCode.trim();
            if (dictCode.toLowerCase().indexOf("select ") == 0 && (fieldName == null || records.size() > 0)) {
                if (dictCode.endsWith(";"))
                    dictCode = dictCode.substring(0, dictCode.length() - 1);
                String str = "SELECT * FROM (" + dictCode + ") temp ";
                if (records != null &&
                        records.size() < 100) {
                    HashSet<String> hashSet = new HashSet();
                    for (byte b = 0; b < records.size(); b++) {
                        Map map = records.get(b);
                        if (map != null) {
                            String str2 = b.a(map, fieldName);
                            if (str2 != null)
                                hashSet.add(str2.toString());
                        }
                    }
                    String str1 = "'" + StringUtils.join(hashSet, "','") + "'";
                    str = str + "WHERE temp.value IN (" + str1 + ")";
                }
                list = ((OnlCgreportHeadMapper) getBaseMapper()).queryDictListBySql(str);
            } else {
                list = this.sysBaseAPI.queryDictItemsByCode(dictCode);
            }
        }
        return list;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\service\a\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
