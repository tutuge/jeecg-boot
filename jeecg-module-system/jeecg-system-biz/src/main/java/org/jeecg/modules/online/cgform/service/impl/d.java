package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLowApp;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.CgformEnum;
import org.jeecg.common.constant.enums.LowAppAopEnum;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.*;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.jeecg.modules.online.cgform.b1.bConstant;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaImportInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.cgform.enhance.impl.http.a;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.mapper.*;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.config.bAttribute.c;
import org.jeecg.modules.online.config.dUtil.eDbTableHandleI;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOne;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOneToMany;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.jeecgframework.codegenerate.generate.pojo.TableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.MainTableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.SubTableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service("onlCgformHeadServiceImpl")
public class d extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {
       private static final Logger a = LoggerFactory.getLogger(d.class);

    @Autowired
    private IOnlCgformFieldService fieldService;

    @Autowired
    private IOnlCgformIndexService indexService;

    @Autowired
    private OnlCgformEnhanceJsMapper onlCgformEnhanceJsMapper;

    @Autowired
    private OnlCgformButtonMapper onlCgformButtonMapper;

    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;

    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private bConstant dataBaseConfig;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    private IOnlAuthRelationService onlAuthRelationService;

    @Autowired
    private a cgformEnhanceJavaHttp;

    @Autowired
    private bConstant cgformEnhanceJavaListHttp;

    @Value("${jeecg.online.datasource:}")
    private String onlineDatasource;

    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseApi;

    @Transactional(rollbackFor = {Exception.class})
    public Result<?> addAll(a model) {
        String str = UUID.randomUUID().toString().replace("-", "");
        OnlCgformHead onlCgformHead = model.getHead();
        List<OnlCgformField> list = model.getFields();
        List list1 = model.getIndexs();
        onlCgformHead.setId(str);
        boolean bool = false;
        for (byte b1 = 0; b1 < list.size(); b1++) {
            OnlCgformField onlCgformField = list.get(b1);
            onlCgformField.setId(null);
            onlCgformField.setCgformHeadId(str);
            if (onlCgformField.getOrderNum() == null)
                onlCgformField.setOrderNum(Integer.valueOf(b1));
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable()) && oConvertUtils.isNotEmpty(onlCgformField.getMainField()))
                bool = true;
            a(onlCgformField);
            if (onlCgformField.getDbIsPersist() == null)
                onlCgformField.setDbIsPersist(bConstant.b);
        }
        for (OnlCgformIndex onlCgformIndex : list1) {
            onlCgformIndex.setId(null);
            onlCgformIndex.setCgformHeadId(str);
            onlCgformIndex.setIsDbSynch("N");
            onlCgformIndex.setDelFlag(CommonConstant.DEL_FLAG_0);
        }
        onlCgformHead.setIsDbSynch("N");
        onlCgformHead.setQueryMode("single");
        onlCgformHead.setTableVersion(Integer.valueOf(1));
        onlCgformHead.setCopyType(Integer.valueOf(0));
        if (onlCgformHead.getTableType().intValue() == 3 && onlCgformHead.getTabOrderNum() == null)
            onlCgformHead.setTabOrderNum(Integer.valueOf(1));
        save(onlCgformHead);
        this.fieldService.saveBatch(list);
        this.indexService.saveBatch(list1);
        a(onlCgformHead, list);
        if (onlCgformHead.getTableType().intValue() == 3 && bool)
            this.onlCgformFieldService.clearCacheOnlineConfig();
        return Result.ok("添加成功");
    }

    @Transactional(rollbackFor = {Exception.class})
    public Result<?> editAll(a model) {
        OnlCgformHead onlCgformHead1 = model.getHead();
        OnlCgformHead onlCgformHead2 = (OnlCgformHead) getById(onlCgformHead1.getId());
        if (onlCgformHead2 == null)
            return Result.error("未找到对应实体");
        String str = onlCgformHead2.getIsDbSynch();
        if (bConstant.a(onlCgformHead2, onlCgformHead1))
            str = "N";
        Integer integer = onlCgformHead2.getTableVersion();
        if (integer == null)
            integer = Integer.valueOf(1);
        onlCgformHead1.setTableVersion(integer = Integer.valueOf(integer.intValue() + 1));
        List<OnlCgformField> list = model.getFields();
        List list1 = model.getIndexs();
        ArrayList<OnlCgformField> arrayList1 = new ArrayList();
        ArrayList<OnlCgformField> arrayList2 = new ArrayList();
        for (OnlCgformField onlCgformField : list) {
            String str1 = String.valueOf(onlCgformField.getId());
            a(onlCgformField);
            if (str1.length() == 32) {
                arrayList2.add(onlCgformField);
            } else {
                String str2 = "_pk";
                if (!str2.equals(str1)) {
                    onlCgformField.setId(null);
                    onlCgformField.setCgformHeadId(onlCgformHead1.getId());
                    arrayList1.add(onlCgformField);
                }
            }
            if (onlCgformField.getDbIsPersist() == null)
                onlCgformField.setDbIsPersist(bConstant.b);
        }
        if (arrayList1.size() > 0 &&
                a(arrayList1))
            str = "N";
        int i = 0;
        for (OnlCgformField onlCgformField1 : arrayList2) {
            OnlCgformField onlCgformField2 = (OnlCgformField) this.fieldService.getById(onlCgformField1.getId());
            a(onlCgformField2.getMainTable(), onlCgformHead1.getTableName());
            boolean bool = bConstant.a(onlCgformField2, onlCgformField1);
            if (bool)
                str = "N";
            if (((onlCgformField2.getOrderNum() == null) ? 0 : onlCgformField2.getOrderNum().intValue()) > i)
                i = onlCgformField2.getOrderNum().intValue();
            if ("Y".equals(onlCgformHead2.getIsDbSynch()) && !onlCgformField1.getDbFieldName().equals(onlCgformField2.getDbFieldName()))
                onlCgformField1.setDbFieldNameOld(onlCgformField2.getDbFieldName());
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().eq(OnlCgformField::getId, onlCgformField1.getId());
            if (onlCgformField1.getFieldValidType() == null)
                updateWrapper.lambda().set(OnlCgformField::getFieldValidType, "");
            this.fieldService.update(onlCgformField1, (Wrapper) updateWrapper);
        }
        for (OnlCgformField onlCgformField : arrayList1) {
            if (onlCgformField.getOrderNum() == null)
                onlCgformField.setOrderNum(Integer.valueOf(++i));
            this.fieldService.save(onlCgformField);
        }
        List list2 = this.indexService.getCgformIndexsByCgformId(onlCgformHead1.getId());
        ArrayList<OnlCgformIndex> arrayList3 = new ArrayList();
        ArrayList<OnlCgformIndex> arrayList4 = new ArrayList();
        for (OnlCgformIndex onlCgformIndex : list1) {
            String str1 = String.valueOf(onlCgformIndex.getId());
            if (str1.length() == 32) {
                arrayList4.add(onlCgformIndex);
                continue;
            }
            onlCgformIndex.setId(null);
            onlCgformIndex.setIsDbSynch("N");
            onlCgformIndex.setDelFlag(CommonConstant.DEL_FLAG_0);
            onlCgformIndex.setCgformHeadId(onlCgformHead1.getId());
            arrayList3.add(onlCgformIndex);
        }
        for (OnlCgformIndex onlCgformIndex : list2) {
            boolean bool = list1.stream().anyMatch(paramOnlCgformIndex2 -> paramOnlCgformIndex1.getId().equals(paramOnlCgformIndex2.getId()));
            if (!bool) {
                onlCgformIndex.setDelFlag(CommonConstant.DEL_FLAG_1);
                arrayList4.add(onlCgformIndex);
                str = "N";
            }
        }
        if (arrayList3.size() > 0) {
            str = "N";
            this.indexService.saveBatch(arrayList3);
        }
        for (OnlCgformIndex onlCgformIndex1 : arrayList4) {
            OnlCgformIndex onlCgformIndex2 = (OnlCgformIndex) this.indexService.getById(onlCgformIndex1.getId());
            boolean bool = bConstant.a(onlCgformIndex2, onlCgformIndex1);
            if (bool) {
                str = "N";
                onlCgformIndex1.setIsDbSynch("N");
            }
            this.indexService.updateById(onlCgformIndex1);
        }
        if (model.getDeleteFieldIds().size() > 0) {
            List list3 = model.getDeleteFieldIds();
            for (String str1 : list3) {
                OnlCgformField onlCgformField = (OnlCgformField) this.fieldService.getById(str1);
                if (onlCgformField != null) {
                    if (bConstant.b.equals(onlCgformField.getDbIsPersist()))
                        str = "N";
                    a(onlCgformField.getMainTable(), onlCgformHead1.getTableName());
                    this.fieldService.removeById(str1);
                }
            }
        }
        onlCgformHead1.setIsDbSynch(str);
        updateById(onlCgformHead1);
        a(onlCgformHead1, list);
        b(onlCgformHead1, list);
        return Result.ok("全部修改成功");
    }

    private boolean a(List<OnlCgformField> paramList) {
        if (paramList == null || paramList.size() == 0)
            return false;
        boolean bool = false;
        for (OnlCgformField onlCgformField : paramList) {
            if (bConstant.b.equals(onlCgformField.getDbIsPersist())) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    private void a(String paramString1, String paramString2) {
        if (oConvertUtils.isNotEmpty(paramString1)) {
            LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, paramString1);
            OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) lambdaQueryWrapper);
            if (onlCgformHead != null && oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
                String str = onlCgformHead.getSubTableStr();
                String[] arrayOfString = str.split(",");
                ArrayList<String> arrayList = new ArrayList();
                for (String str1 : arrayOfString) {
                    if (!str1.equals(paramString2))
                        arrayList.add(str1);
                }
                onlCgformHead.setSubTableStr(String.join(",", (Iterable) arrayList));
                ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead);
            }
        }
    }

    public void doDbSynch(String code, String synMethod) throws HibernateException, IOException, TemplateException, SQLException, a {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(code);
        if (onlCgformHead == null)
       throws new AException("实体配置不存在");
        String str = onlCgformHead.getTableName();
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, bConstant.b);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list = this.fieldService.list((Wrapper) lambdaQueryWrapper);
        a a1 = new a();
        a1.setTableName(str);
        a1.setJformPkType(onlCgformHead.getIdType());
        a1.setJformPkSequence(onlCgformHead.getIdSequence());
        a1.setContent(onlCgformHead.getTableTxt());
        a1.setColumns(list);
        bConstant b1 = getOnlineDataBaseConfig();
        a1.setDbConfig(b1);
        DbType dbType = eDbTableHandleI.c(b1);
        if ("normal".equals(synMethod) && !dbType.equals(DbType.SQLITE)) {
            long l = System.currentTimeMillis();
            boolean bool = eDbTableHandleI.a(str, b1).booleanValue();
            if (bool) {
                org.jeecg.modules.online.config.dUtil.d d1 = new org.jeecg.modules.online.config.dUtil.d(b1);
                List list1 = d1.b(a1);
                for (String str1 : list1) {
                    if (oConvertUtils.isEmpty(str1) || oConvertUtils.isEmpty(str1.trim()))
                        continue;
                    String[] arrayOfString = str1.split(";");
                    if (arrayOfString == null || arrayOfString.length > 1) ;
                    for (String str2 : arrayOfString) {
                        if (!oConvertUtils.isEmpty(str2) && !oConvertUtils.isEmpty(str2.trim()))
                            ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str2);
                    }
                }
                List list2 = this.indexService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformIndex::getCgformHeadId, code));
                for (OnlCgformIndex onlCgformIndex : list2) {
                    if ("N".equals(onlCgformIndex.getIsDbSynch()) || CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag())) {
                        String str1 = d1.b(onlCgformIndex.getIndexName(), str);
                        if (this.indexService.isExistIndex(str1)) {
                            String str2 = d1.a(onlCgformIndex.getIndexName(), str);
                            try {
                                ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str2);
                                if (CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag()))
                                    this.indexService.removeById(onlCgformIndex.getId());
                            } catch (Exception exception) {
                                a.error("删除表【" + str + "】索引(" + onlCgformIndex.getIndexName() + ")失败!", exception);
                            }
                            continue;
                        }
                        if (CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag()))
                            this.indexService.removeById(onlCgformIndex.getId());
                    }
                }
            } else {
                org.jeecg.modules.online.config.dUtil.d.a(a1);
            }
        } else if ("force".equals(synMethod) || dbType.equals(DbType.SQLITE)) {
            DbTableHandleI dbTableHandleI = eDbTableHandleI.getTableHandle();
            String str1 = dbTableHandleI.dropTableSQL(str);
            ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str1);
            org.jeecg.modules.online.config.dUtil.d.a(a1);
        }
        this.indexService.createIndex(code, eDbTableHandleI.getDatabaseType(), str);
        onlCgformHead.setIsDbSynch("Y");
        if (onlCgformHead.getTableVersion().intValue() == 1)
            onlCgformHead.setTableVersion(Integer.valueOf(2));
        updateById(onlCgformHead);
    }

    public void deleteRecordAndTable(String id) throws AException, SQLException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(id);
        if (onlCgformHead == null)
       throws new AException("实体配置不存在");
        long l = System.currentTimeMillis();
        boolean bool = eDbTableHandleI.a(onlCgformHead.getTableName()).booleanValue();
        if (bool) {
            String str = eDbTableHandleI.getTableHandle().dropTableSQL(onlCgformHead.getTableName());
            ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str);
        }
        deleteRecord(id);
    }

    public void deleteRecord(String id) throws AException, SQLException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(id);
        if (onlCgformHead == null)
       throws new AException("实体配置不存在");
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformHead::getPhysicId, id);
        List list = ((OnlCgformHeadMapper) this.baseMapper).selectList((Wrapper) lambdaQueryWrapper);
        if (list != null && list.size() > 0)
            for (OnlCgformHead onlCgformHead1 : list)
                a(onlCgformHead1.getId());
        a(onlCgformHead);
        a(id);
        if (onlCgformHead.getTableType().intValue() == 3)
            this.onlCgformFieldService.clearCacheOnlineConfig();
    }

    private void a(String paramString) {
        ((OnlCgformHeadMapper) this.baseMapper).deleteById(paramString);
        LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
        lambdaQueryWrapper1.eq(OnlCgformField::getCgformHeadId, paramString);
        this.fieldService.remove((Wrapper) lambdaQueryWrapper1);
        LambdaQueryWrapper lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgformIndex::getCgformHeadId, paramString);
        this.indexService.remove((Wrapper) lambdaQueryWrapper2);
        LambdaQueryWrapper lambdaQueryWrapper3 = new LambdaQueryWrapper();
        lambdaQueryWrapper3.eq(OnlAuthRelation::getCgformId, paramString);
        this.onlAuthRelationService.remove((Wrapper) lambdaQueryWrapper3);
        LambdaQueryWrapper lambdaQueryWrapper4 = new LambdaQueryWrapper();
        lambdaQueryWrapper4.eq(OnlAuthData::getCgformId, paramString);
        this.onlAuthDataService.remove((Wrapper) lambdaQueryWrapper4);
        LambdaQueryWrapper lambdaQueryWrapper5 = new LambdaQueryWrapper();
        lambdaQueryWrapper5.eq(OnlAuthPage::getCgformId, paramString);
        this.onlAuthPageService.remove((Wrapper) lambdaQueryWrapper5);
    }

    private void a(OnlCgformHead paramOnlCgformHead) {
        if (paramOnlCgformHead.getTableType().intValue() == 3) {
            LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, paramOnlCgformHead.getId());
            List list = this.fieldService.list((Wrapper) lambdaQueryWrapper);
            String str = null;
            for (OnlCgformField onlCgformField : list) {
                str = onlCgformField.getMainTable();
                if (oConvertUtils.isNotEmpty(str))
                    break;
            }
            if (oConvertUtils.isNotEmpty(str)) {
                OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead != null) {
                    String str1 = onlCgformHead.getSubTableStr();
                    if (oConvertUtils.isNotEmpty(str1)) {
                        List<? extends CharSequence> list1 = (List) Arrays.<String>asList(str1.split(",")).stream().collect(Collectors.toList());
                        list1.remove(paramOnlCgformHead.getTableName());
                        onlCgformHead.setSubTableStr(String.join(",", list1));
                        ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead);
                    }
                }
            }
        }
    }

    public List<Map<String, Object>> queryListData(String sql) {
        return ((OnlCgformHeadMapper) this.baseMapper).queryList(sql);
    }

    public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
    }

    public OnlCgformEnhanceJs queryEnhance(String code, String type) {
        return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne((Wrapper) ((LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformEnhanceJs::getCgJsType, type)).eq(OnlCgformEnhanceJs::getCgformHeadId, code));
    }

    public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
    }

    public OnlCgformEnhanceSql queryEnhanceSql(String formId, String buttonCode) {
        return (OnlCgformEnhanceSql) this.onlCgformEnhanceSqlMapper.selectOne((Wrapper) ((LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformEnhanceSql::getCgformHeadId, formId)).eq(OnlCgformEnhanceSql::getButtonCode, buttonCode));
    }

    public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        return (OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne((Wrapper) lambdaQueryWrapper);
    }

    public List<OnlCgformButton> queryButtonList(String code, boolean isListButton) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, code);
        if (isListButton) {
            lambdaQueryWrapper.in(OnlCgformButton::getButtonStyle, new Object[]{"link", "button"});
        } else {
            lambdaQueryWrapper.eq(OnlCgformButton::getButtonStyle, "form");
        }
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList((Wrapper) lambdaQueryWrapper);
    }

    public List<OnlCgformButton> queryButtonList(String code) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, code);
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList((Wrapper) lambdaQueryWrapper);
    }

    public List<String> queryOnlinetables() {
        return ((OnlCgformHeadMapper) this.baseMapper).queryOnlinetables();
    }

    @AutoLowApp(action = LowAppAopEnum.CGFORM_DB_IMPORT, bizType = "cgform")
    @Transactional(rollbackFor = {Exception.class})
    public void saveDbTable2Online(String tbname) {
        OnlCgformHead onlCgformHead = new OnlCgformHead();
        onlCgformHead.setTableType(Integer.valueOf(1));
        onlCgformHead.setIsCheckbox("Y");
        onlCgformHead.setIsDbSynch("Y");
        onlCgformHead.setIsTree("N");
        onlCgformHead.setIsPage("Y");
        onlCgformHead.setQueryMode("group");
        onlCgformHead.setTableName(tbname.toLowerCase());
        onlCgformHead.setTableTxt(tbname);
        onlCgformHead.setTableVersion(Integer.valueOf(1));
        onlCgformHead.setFormTemplate("1");
        onlCgformHead.setCopyType(Integer.valueOf(0));
        onlCgformHead.setIsDesForm("N");
        onlCgformHead.setScroll(Integer.valueOf(1));
        onlCgformHead.setThemeTemplate("normal");
        String str = UUIDGenerator.generate();
        onlCgformHead.setId(str);
        ArrayList<OnlCgformField> arrayList = new ArrayList();
        try {
            List<ColumnVo> list = DbReadTableUtil.readOriginalTableColumn(tbname);
            for (byte b1 = 0; b1 < list.size(); b1++) {
                ColumnVo columnVo = list.get(b1);
                String str1 = columnVo.getFieldDbName();
                OnlCgformField onlCgformField = new OnlCgformField();
                onlCgformField.setCgformHeadId(str);
                onlCgformField.setDbFieldNameOld(columnVo.getFieldDbName().toLowerCase());
                onlCgformField.setDbFieldName(columnVo.getFieldDbName().toLowerCase());
                if (oConvertUtils.isNotEmpty(columnVo.getFiledComment())) {
                    onlCgformField.setDbFieldTxt(columnVo.getFiledComment());
                } else {
                    onlCgformField.setDbFieldTxt(columnVo.getFieldName());
                }
                onlCgformField.setDbIsKey(Integer.valueOf(0));
                onlCgformField.setIsShowForm(Integer.valueOf(1));
                onlCgformField.setIsQuery(Integer.valueOf(0));
                onlCgformField.setFieldMustInput("0");
                onlCgformField.setIsShowList(Integer.valueOf(1));
                onlCgformField.setOrderNum(Integer.valueOf(b1 + 1));
                onlCgformField.setQueryMode("single");
                onlCgformField.setDbLength(Integer.valueOf(oConvertUtils.getInt(columnVo.getPrecision())));
                onlCgformField.setFieldLength(Integer.valueOf(120));
                onlCgformField.setDbPointLength(Integer.valueOf(oConvertUtils.getInt(columnVo.getScale())));
                onlCgformField.setFieldShowType("text");
                onlCgformField.setDbIsNull(Integer.valueOf("Y".equals(columnVo.getNullable()) ? 1 : 0));
                onlCgformField.setIsReadOnly(Integer.valueOf(0));
                if ("id".equalsIgnoreCase(str1)) {
                    String[] arrayOfString = {"java.lang.Integer", "java.lang.Long"};
                    String str2 = columnVo.getFieldType();
                    if (Arrays.<String>asList(arrayOfString).contains(str2)) {
                        onlCgformHead.setIdType("NATIVE");
                    } else {
                        onlCgformHead.setIdType("UUID");
                    }
                    onlCgformField.setDbIsKey(Integer.valueOf(1));
                    onlCgformField.setIsShowForm(Integer.valueOf(0));
                    onlCgformField.setIsShowList(Integer.valueOf(0));
                    onlCgformField.setIsReadOnly(Integer.valueOf(1));
                }
                if ("create_by".equalsIgnoreCase(str1) || "create_time".equalsIgnoreCase(str1) || "update_by".equalsIgnoreCase(str1) || "update_time"
                        .equalsIgnoreCase(str1) || "sys_org_code".equalsIgnoreCase(str1)) {
                    onlCgformField.setIsShowForm(Integer.valueOf(0));
                    onlCgformField.setIsShowList(Integer.valueOf(0));
                }
                if ("java.lang.Integer".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("int");
                } else if ("java.lang.Long".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("int");
                } else if ("java.util.Date".equalsIgnoreCase(columnVo.getFieldType())) {
                    if ("datetime".equals(columnVo.getFieldDbType())) {
                        onlCgformField.setDbType("Datetime");
                        onlCgformField.setFieldShowType("datetime");
                    } else {
                        onlCgformField.setDbType("Date");
                        onlCgformField.setFieldShowType("date");
                    }
                } else if ("java.lang.Double".equalsIgnoreCase(columnVo.getFieldType()) || "java.lang.Float"
                        .equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("double");
                } else if ("java.math.BigDecimal".equalsIgnoreCase(columnVo.getFieldType()) || "BigDecimal".equalsIgnoreCase(columnVo.getFieldType())) {
                    onlCgformField.setDbType("BigDecimal");
                } else if ("byte[]".equalsIgnoreCase(columnVo.getFieldType()) || columnVo.getFieldType().contains("blob")) {
                    onlCgformField.setDbType("Blob");
                    columnVo.setCharmaxLength(null);
                } else if ("java.lang.Object".equals(columnVo.getFieldType()) && ("text".equalsIgnoreCase(columnVo.getFieldDbType()) || "ntext".equalsIgnoreCase(columnVo.getFieldDbType()))) {
                    onlCgformField.setDbType("Text");
                    onlCgformField.setFieldShowType("textarea");
                } else if ("java.lang.Object".equals(columnVo.getFieldType()) && "image".equalsIgnoreCase(columnVo.getFieldDbType())) {
                    onlCgformField.setDbType("Blob");
                } else {
                    onlCgformField.setDbType("string");
                }
                if (oConvertUtils.isEmpty(columnVo.getPrecision()) && oConvertUtils.isNotEmpty(columnVo.getCharmaxLength())) {
                    if (Long.valueOf(columnVo.getCharmaxLength()).longValue() >= 3000L) {
                        onlCgformField.setDbType("Text");
                        onlCgformField.setFieldShowType("textarea");
                        try {
                            onlCgformField.setDbLength(Integer.valueOf(columnVo.getCharmaxLength()));
                        } catch (Exception exception) {
                            a.error(exception.getMessage(), exception);
                        }
                    } else {
                        onlCgformField.setDbLength(Integer.valueOf(columnVo.getCharmaxLength()));
                    }
                } else {
                    if (oConvertUtils.isNotEmpty(columnVo.getPrecision())) {
                        onlCgformField.setDbLength(Integer.valueOf(columnVo.getPrecision()));
                    } else if (onlCgformField.getDbType().equals("int")) {
                        onlCgformField.setDbLength(Integer.valueOf(10));
                    }
                    if (oConvertUtils.isNotEmpty(columnVo.getScale()))
                        onlCgformField.setDbPointLength(Integer.valueOf(columnVo.getScale()));
                }
                if (oConvertUtils.getInt(columnVo.getPrecision()) == -1 && oConvertUtils.getInt(columnVo.getScale()) == 0)
                    onlCgformField.setDbType("Text");
                if ("Blob".equals(onlCgformField.getDbType()) || "Text".equals(onlCgformField.getDbType()) || "Date".equals(onlCgformField.getDbType())) {
                    onlCgformField.setDbLength(Integer.valueOf(0));
                    onlCgformField.setDbPointLength(Integer.valueOf(0));
                }
                onlCgformField.setDbIsPersist(bConstant.b);
                arrayList.add(onlCgformField);
            }
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
        }
        if (oConvertUtils.isEmpty(onlCgformHead.getFormCategory()))
            onlCgformHead.setFormCategory("bdfl_include");
        save(onlCgformHead);
        this.fieldService.saveBatch(arrayList);
    }

    private boolean b(String paramString1, String paramString2) {
        if (oConvertUtils.isEmpty(paramString2))
            return false;
        String[] arrayOfString = paramString2.split(",");
        for (String str : arrayOfString) {
            if (str.equalsIgnoreCase(paramString1))
                return true;
        }
        return false;
    }

    private void a(OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList) {
        if (paramOnlCgformHead.getTableType().intValue() == 3) {
            paramOnlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectById(paramOnlCgformHead.getId());
            for (byte b1 = 0; b1 < paramList.size(); b1++) {
                OnlCgformField onlCgformField = paramList.get(b1);
                String str = onlCgformField.getMainTable();
                if (!oConvertUtils.isEmpty(str)) {
                    OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null) {
                        String str1 = onlCgformHead.getSubTableStr();
                        if (oConvertUtils.isEmpty(str1)) {
                            str1 = paramOnlCgformHead.getTableName();
                        } else if (!b(paramOnlCgformHead.getTableName(), str1)) {
                            ArrayList<String> arrayList = new ArrayList(Arrays.asList((Object[]) str1.split(",")));
                            for (byte b2 = 0; b2 < arrayList.size(); b2++) {
                                String str2 = arrayList.get(b2);
                                OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str2));
                                if (onlCgformHead1 != null)
                                    if (paramOnlCgformHead.getTabOrderNum().intValue() < oConvertUtils.getInt(onlCgformHead1.getTabOrderNum(), 0)) {
                                        arrayList.add(b2, paramOnlCgformHead.getTableName());
                                        break;
                                    }
                            }
                            if (arrayList.indexOf(paramOnlCgformHead.getTableName()) < 0)
                                arrayList.add(paramOnlCgformHead.getTableName());
                            str1 = String.join(",", (Iterable) arrayList);
                        }
                        onlCgformHead.setSubTableStr(str1);
                        ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead);
                        break;
                    }
                }
            }
        } else {
            List list = ((OnlCgformHeadMapper) this.baseMapper).selectList((Wrapper) (new LambdaQueryWrapper()).like(OnlCgformHead::getSubTableStr, paramOnlCgformHead.getTableName()));
            if (list != null && list.size() > 0)
                for (OnlCgformHead onlCgformHead : list) {
                    String str = onlCgformHead.getSubTableStr();
                    if (onlCgformHead.getSubTableStr().equals(paramOnlCgformHead.getTableName())) {
                        str = "";
                    } else if (onlCgformHead.getSubTableStr().startsWith(paramOnlCgformHead.getTableName() + ",")) {
                        str = str.replace(paramOnlCgformHead.getTableName() + ",", "");
                    } else if (onlCgformHead.getSubTableStr().endsWith("," + paramOnlCgformHead.getTableName())) {
                        str = str.replace("," + paramOnlCgformHead.getTableName(), "");
                    } else if (onlCgformHead.getSubTableStr().indexOf("," + paramOnlCgformHead.getTableName() + ",") != -1) {
                        str = str.replace("," + paramOnlCgformHead.getTableName() + ",", ",");
                    }
                    onlCgformHead.setSubTableStr(str);
                    ((OnlCgformHeadMapper) this.baseMapper).updateById(onlCgformHead);
                }
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public String saveManyFormData(String code, JSONObject json, String xAccessToken) throws AException, BusinessException {
        OnlCgformHead onlCgformHead = getTable(code);
        String str1 = "add";
        executeEnhanceJava(str1, "start", onlCgformHead, json);
        String str2 = bConstant.f(onlCgformHead.getTableName());
        if (onlCgformHead.getTableType().intValue() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str)) {
                String[] arrayOfString = str.split(",");
                for (String str3 : arrayOfString) {
                    JSONArray jSONArray = json.getJSONArray(str3);
                    if (jSONArray != null && jSONArray.size() != 0) {
                        OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str3));
                        if (onlCgformHead1 != null) {
                            List list = this.fieldService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId()));
                            String str4 = "", str5 = null;
                            for (OnlCgformField onlCgformField : list) {
                                if (oConvertUtils.isEmpty(onlCgformField.getMainField()))
                                    continue;
                                str4 = onlCgformField.getDbFieldName();
                                String str6 = onlCgformField.getMainField();
                                if (json.get(str6.toLowerCase()) != null)
                                    str5 = json.getString(str6.toLowerCase());
                                if (json.get(str6.toUpperCase()) != null)
                                    str5 = json.getString(str6.toUpperCase());
                            }
                            for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                                JSONObject jSONObject = jSONArray.getJSONObject(b1);
                                if (str5 != null)
                                    jSONObject.put(str4, str5);
                                this.fieldService.saveFormData(list, str3, jSONObject);
                            }
                        }
                    }
                }
            }
        }
        if ("Y".equals(onlCgformHead.getIsTree())) {
            this.fieldService.saveTreeFormData(onlCgformHead.getId(), str2, json, onlCgformHead.getTreeIdField(), onlCgformHead.getTreeParentIdField());
        } else {
            this.fieldService.saveFormData(onlCgformHead.getId(), str2, json, false);
        }
        executeEnhanceSql(str1, onlCgformHead.getId(), json);
        executeEnhanceJava(str1, "end", onlCgformHead, json);
        return onlCgformHead.getTableName();
    }

    public Map<String, Object> querySubFormData(String table, String mainId) throws AException {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, table));
        if (onlCgformHead == null)
       throws new AException("数据库子表[" + table + "]不存在");
        List list = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
        String str = null;
        for (OnlCgformField onlCgformField : list) {
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                str = onlCgformField.getDbFieldName();
                break;
            }
        }
        List<Map> list1 = this.fieldService.querySubFormData(list, table, str, mainId);
        if (list1 != null && list1.size() == 0)
       throws new AException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
        if (list1.size() > 1)
       throws new AException("数据库子表[" + table + "]存在多条记录,主表ID为" + mainId);
        return list1.get(0);
    }

    public List<Map<String, Object>> queryManySubFormData(String table, String mainId) throws AException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, table));
        if (onlCgformHead == null)
       throws new AException("数据库子表[" + table + "]不存在");
        List list1 = this.fieldService.queryFormFields(onlCgformHead.getId(), false);
        if (list1 == null || list1.size() == 0)
       throws new AException("找不到子表字段，请确认配置是否正确!");
        String str1 = null, str2 = null, str3 = null;
        for (OnlCgformField onlCgformField1 : list1) {
            if (oConvertUtils.isNotEmpty(onlCgformField1.getMainField())) {
                str1 = onlCgformField1.getDbFieldName();
                str2 = onlCgformField1.getMainTable();
                str3 = onlCgformField1.getMainField();
                break;
            }
        }
        ArrayList<OnlCgformField> arrayList = new ArrayList();
        OnlCgformField onlCgformField = new OnlCgformField();
        onlCgformField.setDbFieldName(str3);
        arrayList.add(onlCgformField);
        Map map = this.fieldService.queryFormData(arrayList, str2, mainId);
        String str4 = oConvertUtils.getString(oConvertUtils.getString(map.get(str3)), oConvertUtils.getString(map.get(str3.toUpperCase())));
        List list2 = this.fieldService.querySubFormData(list1, table, str1, str4);
        if (list2 != null && list2.size() == 0)
       throws new AException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
        ArrayList<Map> arrayList1 = new ArrayList(list2.size());
        for (Map map1 : list2)
            arrayList1.add(bConstant.a(map1));
        return (List) arrayList1;
    }

    public Map<String, Object> queryManyFormData(String code, String id) throws AException {
        OnlCgformHead onlCgformHead = getTable(code);
        List list = this.fieldService.queryFormFields(onlCgformHead.getId(), true);
        if (list == null || list.isEmpty()) {
                throws new AException("找不到字段，请确认配置是否正确!");
        }

        Map<String, String[]> map = this.fieldService.queryFormData(list, onlCgformHead.getTableName(), id);
        if (onlCgformHead.getTableType().intValue() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str)) {
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead1 != null) {
                        List list1 = this.fieldService.queryFormFields(onlCgformHead1.getId(), false);
                        String str2 = "", str3 = null;
                        for (OnlCgformField onlCgformField : list1) {
                            if (oConvertUtils.isEmpty(onlCgformField.getMainField()))
                                continue;
                            str2 = onlCgformField.getDbFieldName();
                            String str4 = onlCgformField.getMainField();
                            str3 = bConstant.a(map, str4);
                        }
                        List list2 = this.fieldService.querySubFormData(list1, str1, str2, str3);
                        if (list2 == null || list2.size() == 0) {
                            map.put(str1, new String[0]);
                        } else {
                            map.put(str1, bConstant.d(list2));
                        }
                    }
                }
            }
        }
        return (Map) map;
    }

    @Transactional(rollbackFor = {Exception.class})
    public String editManyFormData(String code, JSONObject json) throws AException, BusinessException {
        OnlCgformHead onlCgformHead = getTable(code);
        String str1 = "edit";
        executeEnhanceJava(str1, "start", onlCgformHead, json);
        String str2 = onlCgformHead.getTableName();
        if ("Y".equals(onlCgformHead.getIsTree())) {
            this.fieldService.editTreeFormData(onlCgformHead.getId(), str2, json, onlCgformHead.getTreeIdField(), onlCgformHead.getTreeParentIdField());
        } else {
            this.fieldService.editFormData(onlCgformHead.getId(), str2, json, false);
        }
        if (onlCgformHead.getTableType().intValue() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str)) {
                String[] arrayOfString = str.split(",");
                for (String str3 : arrayOfString) {
                    OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str3));
                    if (onlCgformHead1 != null) {
                        List list = this.fieldService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId()));
                        String str4 = "", str5 = null;
                        for (OnlCgformField onlCgformField : list) {
                            if (oConvertUtils.isEmpty(onlCgformField.getMainField()))
                                continue;
                            str4 = onlCgformField.getDbFieldName();
                            String str6 = onlCgformField.getMainField();
                            if (json.get(str6.toLowerCase()) != null)
                                str5 = json.getString(str6.toLowerCase());
                            if (json.get(str6.toUpperCase()) != null)
                                str5 = json.getString(str6.toUpperCase());
                        }
                        if (!oConvertUtils.isEmpty(str5)) {
                            this.fieldService.deleteAutoList(str3, str4, str5);
                            JSONArray jSONArray = json.getJSONArray(str3);
                            if (jSONArray != null && jSONArray.size() != 0)
                                for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                                    JSONObject jSONObject = jSONArray.getJSONObject(b1);
                                    if (str5 != null)
                                        jSONObject.put(str4, str5);
                                    this.fieldService.saveFormData(list, str3, jSONObject);
                                }
                        }
                    }
                }
            }
        }
        executeEnhanceJava(str1, "end", onlCgformHead, json);
        executeEnhanceSql(str1, onlCgformHead.getId(), json);
        return str2;
    }

    private OnlCgformEnhanceJava a(String paramString1, String paramString2, String paramString3) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, paramString1);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, paramString2);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, paramString3);
        return (OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne((Wrapper) lambdaQueryWrapper);
    }

    private Object b(String paramString1, String paramString2, String paramString3) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, paramString1);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, paramString2);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, paramString3);
        OnlCgformEnhanceJava onlCgformEnhanceJava = (OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne((Wrapper) lambdaQueryWrapper);
        return a(onlCgformEnhanceJava);
    }

    private void a(JSONObject paramJSONObject, Object paramObject, String paramString, OnlCgformEnhanceJava paramOnlCgformEnhanceJava) throws BusinessException {
        if (paramObject != null && paramObject instanceof CgformEnhanceJavaInter) {
            CgformEnhanceJavaInter cgformEnhanceJavaInter = (CgformEnhanceJavaInter) paramObject;
            cgformEnhanceJavaInter.execute(paramString, paramJSONObject);
        } else if (paramObject != null && paramObject instanceof a) {
            ((a) paramObject).execute(paramString, paramJSONObject, paramOnlCgformEnhanceJava);
        }
    }

    public void executeEnhanceJava(String buttonCode, String eventType, OnlCgformHead head, JSONObject json) throws BusinessException {
        OnlCgformEnhanceJava onlCgformEnhanceJava = a(buttonCode, eventType, head.getId());
        Object object = a(onlCgformEnhanceJava);
        a(json, object, head.getTableName(), onlCgformEnhanceJava);
    }

    public void executeEnhanceExport(OnlCgformHead head, List<Map<String, Object>> dataList) throws BusinessException {
        executeEnhanceList(head, "export", dataList);
    }

    public EnhanceDataEnum executeEnhanceImport(OnlCgformHead head, JSONObject json) throws BusinessException {
        OnlCgformEnhanceJava onlCgformEnhanceJava = a("import", "start", head.getId());
        Object object = a(onlCgformEnhanceJava);
        if (object != null && object instanceof CgformEnhanceJavaImportInter) {
            CgformEnhanceJavaImportInter cgformEnhanceJavaImportInter = (CgformEnhanceJavaImportInter) object;
            return cgformEnhanceJavaImportInter.execute(head.getTableName(), json);
        }
        return EnhanceDataEnum.INSERT;
    }

    public void executeEnhanceList(OnlCgformHead head, String buttonCode, List<Map<String, Object>> dataList) throws BusinessException {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, buttonCode);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, head.getId());
        List<OnlCgformEnhanceJava> list = this.onlCgformEnhanceJavaMapper.selectList((Wrapper) lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            Object object = a(list.get(0));
            if (object != null && object instanceof CgformEnhanceJavaListInter) {
                CgformEnhanceJavaListInter cgformEnhanceJavaListInter = (CgformEnhanceJavaListInter) object;
                cgformEnhanceJavaListInter.execute(head.getTableName(), dataList);
            } else if (object != null && object instanceof bConstant) {
                ((bConstant) object).execute(head.getTableName(), dataList, list.get(0));
            }
        }
    }

    private Object a(OnlCgformEnhanceJava paramOnlCgformEnhanceJava) {
        if (paramOnlCgformEnhanceJava != null) {
            String str1 = paramOnlCgformEnhanceJava.getCgJavaType();
            String str2 = paramOnlCgformEnhanceJava.getCgJavaValue();
            if (oConvertUtils.isNotEmpty(str2)) {
                Object object = null;
                if ("class".equals(str1)) {
                    try {
                        object = MyClassLoader.getClassByScn(str2).newInstance();
                    } catch (InstantiationException instantiationException) {
                        a.error(instantiationException.getMessage(), instantiationException);
                    } catch (IllegalAccessException illegalAccessException) {
                        a.error(illegalAccessException.getMessage(), illegalAccessException);
                    }
                } else if ("spring".equals(str1)) {
                    object = SpringContextUtils.getBean(str2);
                } else if ("http".equals(str1)) {
                    object = b(paramOnlCgformEnhanceJava);
                }
                return object;
            }
        }
        return null;
    }

    private Object b(OnlCgformEnhanceJava paramOnlCgformEnhanceJava) {
        switch (paramOnlCgformEnhanceJava.getButtonCode()) {
            case "add":
            case "edit":
            case "delete":
            case "import":
                return this.cgformEnhanceJavaHttp;
            case "export":
            case "query":
                return this.cgformEnhanceJavaListHttp;
        }
        return this.cgformEnhanceJavaHttp;
    }

    private OnlCgformEnhanceSql c(String paramString1, String paramString2) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getButtonCode, paramString1);
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getCgformHeadId, paramString2);
        return (OnlCgformEnhanceSql) this.onlCgformEnhanceSqlMapper.selectOne((Wrapper) lambdaQueryWrapper);
    }

    private void a(JSONObject paramJSONObject, OnlCgformEnhanceSql paramOnlCgformEnhanceSql) {
        if (paramOnlCgformEnhanceSql != null && oConvertUtils.isNotEmpty(paramOnlCgformEnhanceSql.getCgbSql())) {
            String str = bConstant.a(paramOnlCgformEnhanceSql.getCgbSql(), paramJSONObject);
            String[] arrayOfString = str.split(";");
            for (String str1 : arrayOfString) {
                if (str1 != null && !"".equals(str1.toLowerCase().trim()))
                    ((OnlCgformHeadMapper) this.baseMapper).executeDDL(str1);
            }
        }
    }

    public void executeEnhanceSql(String buttonCode, String formId, JSONObject json) {
        OnlCgformEnhanceSql onlCgformEnhanceSql = c(buttonCode, formId);
        a(json, onlCgformEnhanceSql);
    }

    public void executeCustomerButton(String buttonCode, String formId, String dataId) throws BusinessException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(formId);
        if (onlCgformHead == null)
            throw new BusinessException("未找到表配置信息");
        OnlCgformEnhanceJava onlCgformEnhanceJava1 = a(buttonCode, "start", formId);
        OnlCgformEnhanceJava onlCgformEnhanceJava2 = a(buttonCode, "end", formId);
        Object object1 = a(onlCgformEnhanceJava1);
        Object object2 = a(onlCgformEnhanceJava2);
        OnlCgformEnhanceSql onlCgformEnhanceSql = c(buttonCode, formId);
        String str = onlCgformHead.getTableName();
        String[] arrayOfString = dataId.split(",");
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, formId);
        List<OnlCgformField> list = this.onlCgformFieldService.list((Wrapper) lambdaQueryWrapper);
        for (String str1 : arrayOfString) {
            Map<String, Object> map = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(bConstant.f(onlCgformHead.getTableName()), bConstant.k(str1));
            map = a(list, map);
            JSONObject jSONObject = JSONObject.parseObject(JSON.toJSONString(map));
            a(jSONObject, object1, str, onlCgformEnhanceJava1);
            a(jSONObject, onlCgformEnhanceSql);
            a(jSONObject, object2, str, onlCgformEnhanceJava2);
        }
    }

    private Map<String, Object> a(List<OnlCgformField> paramList, Map<String, Object> paramMap) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        for (OnlCgformField onlCgformField : paramList) {
            String str1 = onlCgformField.getDbType();
            if ("blob".equalsIgnoreCase(str1) || "text".equalsIgnoreCase(str1))
                continue;
            String str2 = onlCgformField.getDbFieldName();
            Object object = bConstant.b(paramMap, str2);
            hashMap.put(str2, object);
        }
        return (Map) hashMap;
    }

    public List<OnlCgformButton> queryValidButtonList(String headId) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformButton::getCgformHeadId, headId);
        lambdaQueryWrapper.eq(OnlCgformButton::getButtonStatus, "1");
        lambdaQueryWrapper.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList((Wrapper) lambdaQueryWrapper);
    }

    public OnlCgformEnhanceJs queryEnhanceJs(String formId, String cgJsType) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJs::getCgformHeadId, formId);
        lambdaQueryWrapper.eq(OnlCgformEnhanceJs::getCgJsType, cgJsType);
        return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne((Wrapper) lambdaQueryWrapper);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteOneTableInfo(String formId, String dataId) throws BusinessException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(formId);
        if (onlCgformHead == null)
            throw new BusinessException("未找到表配置信息");
        String str1 = bConstant.f(onlCgformHead.getTableName());
        Map map1 = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(str1, dataId);
        if (map1 == null)
            return;
        Map map2 = bConstant.a(map1);
        String str2 = "delete";
        JSONObject jSONObject = JSONObject.parseObject(JSON.toJSONString(map2));
        executeEnhanceJava(str2, "start", onlCgformHead, jSONObject);
        updateParentNode(onlCgformHead, dataId);
        if (onlCgformHead.getTableType().intValue() == 2) {
            this.fieldService.deleteAutoListMainAndSub(onlCgformHead, dataId);
        } else {
            String str = "delete from " + str1 + " where id = '" + dataId + "'";
            ((OnlCgformHeadMapper) this.baseMapper).deleteOne(str);
        }
        executeEnhanceSql(str2, formId, jSONObject);
        executeEnhanceJava(str2, "end", onlCgformHead, jSONObject);
    }

    @Deprecated
    public JSONObject queryFormItem(OnlCgformHead head, String username) {
        List list = this.fieldService.queryAvailableFields(head.getId(), head.getTableName(), head.getTaskId(), false);
        ArrayList arrayList = new ArrayList();
        if (oConvertUtils.isEmpty(head.getTaskId())) {
            List list1 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
            if (list1 != null && list1.size() > 0 && list1.get(0) != null)
                arrayList.addAll(list1);
        } else {
            List list1 = this.fieldService.queryDisabledFields(head.getTableName(), head.getTaskId());
            if (list1 != null && list1.size() > 0 && list1.get(0) != null)
                arrayList.addAll(list1);
        }
        JSONObject jSONObject = bConstant.a(list, arrayList, null);
        if (head.getTableType().intValue() == 2) {
            String str = head.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str))
                for (String str1 : str.split(",")) {
                    OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead != null) {
                        List list1 = this.fieldService.queryAvailableFields(onlCgformHead.getId(), onlCgformHead.getTableName(), head.getTaskId(), false);
                        List list2 = new ArrayList();
                        if (oConvertUtils.isNotEmpty(head.getTaskId())) {
                            List list3 = this.fieldService.queryDisabledFields(onlCgformHead.getTableName(), head.getTaskId());
                        } else {
                            list2 = this.onlAuthPageService.queryFormDisabledCode(onlCgformHead.getId());
                        }
                        JSONObject jSONObject1 = new JSONObject();
                        if (1 == onlCgformHead.getRelationType().intValue()) {
                            jSONObject1 = bConstant.a(list1, list2, null);
                        } else {
                            jSONObject1.put("columns", bConstant.a(list1, list2));
                        }
                        jSONObject1.put("relationType", onlCgformHead.getRelationType());
                        jSONObject1.put("view", "tab");
                        jSONObject1.put("order", onlCgformHead.getTabOrderNum());
                        jSONObject1.put("formTemplate", onlCgformHead.getFormTemplate());
                        jSONObject1.put("describe", onlCgformHead.getTableTxt());
                        jSONObject1.put("key", onlCgformHead.getTableName());
                        jSONObject.getJSONObject("properties").put(onlCgformHead.getTableName(), jSONObject1);
                    }
                }
        }
        return jSONObject;
    }

    public List<String> generateCode(org.jeecg.modules.online.cgform.model.d model) throws Exception {
        TableVo tableVo = new TableVo();
        tableVo.setEntityName(model.getEntityName());
        tableVo.setEntityPackage(model.getEntityPackage());
        tableVo.setFtlDescription(model.getFtlDescription());
        tableVo.setTableName(model.getTableName());
        tableVo.setSearchFieldNum(Integer.valueOf(-1));
        ArrayList<ColumnVo> arrayList1 = new ArrayList();
        ArrayList<ColumnVo> arrayList2 = new ArrayList();
        a(model.getCode(), arrayList1, arrayList2);
        OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getId, model.getCode()));
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("scroll", (onlCgformHead.getScroll() == null) ? "0" : onlCgformHead.getScroll().toString());
        String str = onlCgformHead.getFormTemplate();
        if (oConvertUtils.isEmpty(str)) {
            tableVo.setFieldRowNum(Integer.valueOf(1));
        } else {
            tableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(str)));
        }
        if ("Y".equals(onlCgformHead.getIsTree())) {
            hashMap.put("pidField", onlCgformHead.getTreeParentIdField());
            hashMap.put("hasChildren", onlCgformHead.getTreeIdField());
            hashMap.put("textField", onlCgformHead.getTreeFieldname());
        }
        if (oConvertUtils.isNotEmpty(model.getVueStyle()))
            hashMap.put("vueStyle", model.getVueStyle());
        tableVo.setExtendParams(hashMap);
        CgformEnum cgformEnum = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        List<String> list = (new CodeGenerateOne(tableVo, arrayList1, arrayList2)).generateCodeFile(model.getProjectPath(), cgformEnum.getTemplatePath(), cgformEnum.getStylePath());
        if (list == null || list.size() == 0) {
            list = new ArrayList();
            list.add(" :::::: 生成失败ERROR提示 :::::: ");
            list.add("1.JeecgBoot项目所在路径是否含有中文或空格，去掉就好了！参考 http://doc.jeecg.com/2088984");
            list.add("2.采用JAR包上线发布，需要做额外配置！参考 http://doc.jeecg.com/2043922");
        }
        return list;
    }

    public List<String> generateOneToMany(org.jeecg.modules.online.cgform.model.d model) throws Exception {
        MainTableVo mainTableVo = new MainTableVo();
        mainTableVo.setEntityName(model.getEntityName());
        mainTableVo.setEntityPackage(model.getEntityPackage());
        mainTableVo.setFtlDescription(model.getFtlDescription());
        mainTableVo.setTableName(model.getTableName());
        OnlCgformHead onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getId, model.getCode()));
        String str = onlCgformHead.getFormTemplate();
        if (oConvertUtils.isEmpty(str)) {
            mainTableVo.setFieldRowNum(Integer.valueOf(1));
        } else {
            mainTableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(str)));
        }
        ArrayList<ColumnVo> arrayList1 = new ArrayList();
        ArrayList<ColumnVo> arrayList2 = new ArrayList();
        a(model.getCode(), arrayList1, arrayList2);
        List list = model.getSubList();
        ArrayList<SubTableVo> arrayList = new ArrayList();
        for (org.jeecg.modules.online.cgform.model.d d1 : list) {
            OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, d1.getTableName()));
            if (onlCgformHead1 == null)
                continue;
            SubTableVo subTableVo = new SubTableVo();
            subTableVo.setEntityName(d1.getEntityName());
            subTableVo.setEntityPackage(model.getEntityPackage());
            subTableVo.setTableName(d1.getTableName());
            subTableVo.setFtlDescription(d1.getFtlDescription());
            Integer integer = onlCgformHead1.getRelationType();
            subTableVo.setForeignRelationType((integer.intValue() == 1) ? "1" : "0");
            ArrayList<ColumnVo> arrayList3 = new ArrayList();
            ArrayList<ColumnVo> arrayList4 = new ArrayList();
            OnlCgformField onlCgformField = a(onlCgformHead1.getId(), arrayList3, arrayList4);
            if (onlCgformField == null)
                continue;
            subTableVo.setOriginalForeignKeys(new String[]{onlCgformField.getDbFieldName()});
            subTableVo.setForeignKeys(new String[]{onlCgformField.getDbFieldName()});
            subTableVo.setForeignMainKeys(new String[]{onlCgformField.getMainField()});
            subTableVo.setColums(arrayList3);
            subTableVo.setOriginalColumns(arrayList4);
            arrayList.add(subTableVo);
        }
        CgformEnum cgformEnum = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        if (oConvertUtils.isNotEmpty(model.getVueStyle())) {
            List<String> list1 = Arrays.asList(cgformEnum.getVueStyle());
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            if (list1.contains(model.getVueStyle())) {
                hashMap.put("vueStyle", model.getVueStyle());
            } else {
                a.warn("你选择的页面代码类型：【" + model.getVueStyle() + "】不支持，采用默认类型:" + (String) list1.get(0) + "生成代码！");
                hashMap.put("vueStyle", list1.get(0));
            }
            mainTableVo.setExtendParams(hashMap);
        }
        if (arrayList == null || arrayList.size() == 0) {
            a.error("你选择的表类型是【主表】，但是没有关联子表，导致生成代码报错！");
            throw new JeecgBootException("你选择的表类型是【主表】，但是没有关联子表，生成代码失败！");
        }
        return (new CodeGenerateOneToMany(mainTableVo, arrayList1, arrayList2, arrayList)).generateCodeFile(model.getProjectPath(), cgformEnum.getTemplatePath(), cgformEnum.getStylePath());
    }

    private OnlCgformField a(String paramString, List<ColumnVo> paramList1, List<ColumnVo> paramList2) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, paramString);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, bConstant.b);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List list = this.fieldService.list((Wrapper) lambdaQueryWrapper);
        OnlCgformField onlCgformField = null;
        for (OnlCgformField onlCgformField1 : list) {
            if (oConvertUtils.isNotEmpty(onlCgformField1.getMainTable()))
                onlCgformField = onlCgformField1;
            ColumnVo columnVo = new ColumnVo();
            columnVo.setFieldLength(onlCgformField1.getFieldLength());
            columnVo.setFieldHref(onlCgformField1.getFieldHref());
            columnVo.setFieldValidType(onlCgformField1.getFieldValidType());
            columnVo.setFieldDefault(onlCgformField1.getDbDefaultVal());
            columnVo.setFieldShowType(onlCgformField1.getFieldShowType());
            columnVo.setFieldOrderNum(onlCgformField1.getOrderNum());
            columnVo.setIsKey((onlCgformField1.getDbIsKey().intValue() == 1) ? "Y" : "N");
            columnVo.setIsShow((onlCgformField1.getIsShowForm().intValue() == 1) ? "Y" : "N");
            columnVo.setIsShowList((onlCgformField1.getIsShowList().intValue() == 1) ? "Y" : "N");
            columnVo.setIsQuery((onlCgformField1.getIsQuery().intValue() == 1) ? "Y" : "N");
            columnVo.setQueryMode(onlCgformField1.getQueryMode());
            columnVo.setDictField(onlCgformField1.getDictField());
            columnVo.setDictTable(onlCgformField1.getDictTable());
            columnVo.setDictText(onlCgformField1.getDictText());
            columnVo.setFieldDbName(onlCgformField1.getDbFieldName());
            columnVo.setFieldName(oConvertUtils.camelName(onlCgformField1.getDbFieldName()));
            columnVo.setFiledComment(onlCgformField1.getDbFieldTxt());
            columnVo.setFieldDbType(onlCgformField1.getDbType());
            columnVo.setFieldType(b(onlCgformField1.getDbType()));
            columnVo.setClassType(onlCgformField1.getFieldShowType());
            columnVo.setClassType_row(onlCgformField1.getFieldShowType());
            if (onlCgformField1.getDbIsNull().intValue() == 0 || "*".equals(onlCgformField1.getFieldValidType()) || "1".equals(onlCgformField1.getFieldMustInput())) {
                columnVo.setNullable("N");
            } else {
                columnVo.setNullable("Y");
            }
            if ("switch".equals(onlCgformField1.getFieldShowType()))
                if (oConvertUtils.isNotEmpty(onlCgformField1.getFieldExtendJson())) {
                    columnVo.setDictField(onlCgformField1.getFieldExtendJson());
                } else {
                    columnVo.setDictField("is_open");
                }
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            if (StringUtils.isNotBlank(onlCgformField1.getFieldExtendJson()))
                try {
                    JSONObject jSONObject = JSONObject.parseObject(onlCgformField1.getFieldExtendJson());
                    if (jSONObject != null)
                        hashMap.putAll(jSONObject.getInnerMap());
                } catch (JSONException jSONException) {
                }
            columnVo.setExtendParams(hashMap);
            if ("popup".equals(onlCgformField1.getFieldShowType())) {
                boolean bool = true;
                Object object = hashMap.get("popupMulti");
                if (object != null)
                    bool = ((Boolean) object).booleanValue();
                hashMap.put("popupMulti", Boolean.valueOf(bool));
            }
            columnVo.setSort("1".equals(onlCgformField1.getSortFlag()) ? "Y" : "N");
            columnVo.setReadonly(Integer.valueOf(1).equals(onlCgformField1.getIsReadOnly()) ? "Y" : "N");
            if (oConvertUtils.isNotEmpty(onlCgformField1.getFieldDefaultValue()) && !onlCgformField1.getFieldDefaultValue().trim().startsWith("${") && !onlCgformField1.getFieldDefaultValue().trim().startsWith("#{") && !onlCgformField1.getFieldDefaultValue().trim().startsWith("{{"))
                columnVo.setDefaultVal(onlCgformField1.getFieldDefaultValue());
            if (("file".equals(onlCgformField1.getFieldShowType()) || "image".equals(onlCgformField1.getFieldShowType())) &&
                    oConvertUtils.isNotEmpty(onlCgformField1.getFieldExtendJson())) {
                JSONObject jSONObject = JSONObject.parseObject(onlCgformField1.getFieldExtendJson());
                if (oConvertUtils.isNotEmpty(jSONObject.getString("uploadnum")))
                    columnVo.setUploadnum(jSONObject.getString("uploadnum"));
            }
            paramList2.add(columnVo);
            if (onlCgformField1.getIsShowForm().intValue() == 1 || onlCgformField1.getIsShowList().intValue() == 1 || onlCgformField1.getIsQuery().intValue() == 1)
                paramList1.add(columnVo);
        }
        return onlCgformField;
    }

    private String b(String paramString) {
        paramString = paramString.toLowerCase();
        if (paramString.indexOf("int") >= 0)
            return "java.lang.Integer";
        if (paramString.indexOf("double") >= 0)
            return "java.lang.Double";
        if (paramString.indexOf("decimal") >= 0)
            return "java.math.BigDecimal";
        if (paramString.indexOf("date") >= 0)
            return "java.util.Date";
        return "java.lang.String";
    }

    public void addCrazyFormData(String tbname, JSONObject json) throws AException, UnsupportedEncodingException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, tbname));
        if (onlCgformHead == null)
       throws new AException("数据库主表[" + tbname + "]不存在");
        if (onlCgformHead.getTableType().intValue() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (str != null) {
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    String str2 = json.getString("sub-table-design_" + str1);
                    if (oConvertUtils.isEmpty(str2)) {
                        str2 = json.getString("sub-table-one2one_" + str1);
                        if (oConvertUtils.isEmpty(str2))
                            continue;
                    }
                    JSONArray jSONArray = JSONArray.parseArray(str2);
                    if (jSONArray != null && jSONArray.size() != 0) {
                        OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
                        if (onlCgformHead1 != null) {
                            List list = this.fieldService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId()));
                            String str3 = "", str4 = null;
                            for (OnlCgformField onlCgformField : list) {
                                if (oConvertUtils.isEmpty(onlCgformField.getMainField()))
                                    continue;
                                str3 = onlCgformField.getDbFieldName();
                                String str5 = onlCgformField.getMainField();
                                str4 = json.getString(str5);
                            }
                            for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                                JSONObject jSONObject = jSONArray.getJSONObject(b1);
                                if (str4 != null)
                                    jSONObject.put(str3, str4);
                                this.fieldService.executeInsertSQL(bConstant.c(str1, list, jSONObject));
                            }
                        }
                    }
                    continue;
                }
            }
        }
        this.fieldService.saveFormData(onlCgformHead.getId(), tbname, json, true);
    }

    public void editCrazyFormData(String tbname, JSONObject json) throws AException, UnsupportedEncodingException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, tbname));
        if (onlCgformHead == null)
       throws new AException("数据库主表[" + tbname + "]不存在");
        if (onlCgformHead.getTableType().intValue() == 2) {
            String str = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty(str)) {
                String[] arrayOfString = str.split(",");
                for (String str1 : arrayOfString) {
                    OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
                    if (onlCgformHead1 == null)
                        continue;
                    List list = this.fieldService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId()));
                    String str2 = "", str3 = null;
                    for (OnlCgformField onlCgformField : list) {
                        if (oConvertUtils.isEmpty(onlCgformField.getMainField()))
                            continue;
                        str2 = onlCgformField.getDbFieldName();
                        String str5 = onlCgformField.getMainField();
                        str3 = json.getString(str5);
                    }
                    if (oConvertUtils.isEmpty(str3))
                        continue;
                    this.fieldService.deleteAutoList(str1, str2, str3);
                    String str4 = json.getString("sub-table-design_" + str1);
                    if (oConvertUtils.isEmpty(str4)) {
                        str4 = json.getString("sub-table-one2one_" + str1);
                        if (oConvertUtils.isEmpty(str4))
                            continue;
                    }
                    if (!oConvertUtils.isEmpty(str4)) {
                        JSONArray jSONArray = JSONArray.parseArray(str4);
                        if (jSONArray != null && jSONArray.size() != 0)
                            for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                                JSONObject jSONObject = jSONArray.getJSONObject(b1);
                                if (str3 != null)
                                    jSONObject.put(str2, str3);
                                this.fieldService.executeInsertSQL(bConstant.c(str1, list, jSONObject));
                            }
                    }
                    continue;
                }
            }
        }
        this.fieldService.editFormData(onlCgformHead.getId(), tbname, json, true);
    }

    public Integer getMaxCopyVersion(String physicId) {
        Integer integer = ((OnlCgformHeadMapper) this.baseMapper).getMaxCopyVersion(physicId);
        return Integer.valueOf((integer == null) ? 0 : integer.intValue());
    }

    public void copyOnlineTableConfig(OnlCgformHead physicTable) throws Exception {
        String str1 = physicTable.getId();
        OnlCgformHead onlCgformHead = new OnlCgformHead();
        String str2 = UUIDGenerator.generate();
        onlCgformHead.setId(str2);
        onlCgformHead.setPhysicId(str1);
        onlCgformHead.setCopyType(Integer.valueOf(1));
        onlCgformHead.setCopyVersion(physicTable.getTableVersion());
        onlCgformHead.setTableVersion(Integer.valueOf(1));
        onlCgformHead.setTableName(d(str1, physicTable.getTableName()));
        onlCgformHead.setTableTxt(physicTable.getTableTxt());
        onlCgformHead.setFormCategory(physicTable.getFormCategory());
        onlCgformHead.setFormTemplate(physicTable.getFormTemplate());
        onlCgformHead.setFormTemplateMobile(physicTable.getFormTemplateMobile());
        onlCgformHead.setIdSequence(physicTable.getIdSequence());
        onlCgformHead.setIdType(physicTable.getIdType());
        onlCgformHead.setIsCheckbox(physicTable.getIsCheckbox());
        onlCgformHead.setIsPage(physicTable.getIsPage());
        onlCgformHead.setIsTree(physicTable.getIsTree());
        onlCgformHead.setQueryMode(physicTable.getQueryMode());
        onlCgformHead.setTableType(Integer.valueOf(1));
        onlCgformHead.setIsDbSynch("N");
        onlCgformHead.setIsDesForm(physicTable.getIsDesForm());
        onlCgformHead.setDesFormCode(physicTable.getDesFormCode());
        onlCgformHead.setTreeParentIdField(physicTable.getTreeParentIdField());
        onlCgformHead.setTreeFieldname(physicTable.getTreeFieldname());
        onlCgformHead.setTreeIdField(physicTable.getTreeIdField());
        onlCgformHead.setRelationType(null);
        onlCgformHead.setTabOrderNum(null);
        onlCgformHead.setSubTableStr(null);
        onlCgformHead.setThemeTemplate(physicTable.getThemeTemplate());
        onlCgformHead.setScroll(physicTable.getScroll());
        onlCgformHead.setExtConfigJson(physicTable.getExtConfigJson());
        List list = this.fieldService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, str1));
        for (OnlCgformField onlCgformField1 : list) {
            OnlCgformField onlCgformField2 = new OnlCgformField();
            onlCgformField2.setCgformHeadId(str2);
            a(onlCgformField1, onlCgformField2);
            this.fieldService.save(onlCgformField2);
        }
        ((OnlCgformHeadMapper) this.baseMapper).insert(onlCgformHead);
    }

    public void initCopyState(List<OnlCgformHead> headList) {
        List list = ((OnlCgformHeadMapper) this.baseMapper).queryCopyPhysicId();
        for (OnlCgformHead onlCgformHead : headList) {
            if (list.contains(onlCgformHead.getId())) {
                onlCgformHead.setHascopy(Integer.valueOf(1));
                continue;
            }
            onlCgformHead.setHascopy(Integer.valueOf(0));
        }
    }

    public void deleteBatch(String ids, String flag) {
        String[] arrayOfString = ids.split(",");
        if ("1".equals(flag)) {
            for (String str : arrayOfString) {
                try {
                    deleteRecordAndTable(str);
                } catch (a a1) {
                    a1.printStackTrace();
                } catch (SQLException sQLException) {
                    sQLException.printStackTrace();
                }
            }
        } else {
            removeByIds(Arrays.asList(arrayOfString));
        }
    }

    public void updateParentNode(OnlCgformHead head, String dataId) {
        if ("Y".equals(head.getIsTree())) {
            String str1 = bConstant.f(head.getTableName());
            String str2 = head.getTreeParentIdField();
            Map map = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(str1, dataId);
            String str3 = null;
            if (map.get(str2) != null && !"0".equals(map.get(str2))) {
                str3 = map.get(str2).toString();
            } else if (map.get(str2.toUpperCase()) != null && !"0".equals(map.get(str2.toUpperCase()))) {
                str3 = map.get(str2.toUpperCase()).toString();
            }
            if (str3 != null) {
                Integer integer = ((OnlCgformHeadMapper) this.baseMapper).queryChildNode(str1, str2, str3);
                if (integer.intValue() == 1) {
                    String str = head.getTreeIdField();
                    this.fieldService.updateTreeNodeNoChild(str1, str, str3);
                }
            }
        }
    }

    private void b(OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList) {
        List list = list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getPhysicId, paramOnlCgformHead.getId()));
        if (list != null && list.size() > 0)
            for (OnlCgformHead onlCgformHead : list) {
                List<OnlCgformField> list1 = this.fieldService.list((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()));
                if (list1 == null || list1.size() == 0) {
                    for (OnlCgformField onlCgformField1 : paramList) {
                        OnlCgformField onlCgformField2 = new OnlCgformField();
                        onlCgformField2.setCgformHeadId(onlCgformHead.getId());
                        a(onlCgformField1, onlCgformField2);
                        this.fieldService.save(onlCgformField2);
                    }
                    continue;
                }
                HashMap<Object, Object> hashMap1 = new HashMap<>(5);
                for (OnlCgformField onlCgformField : list1)
                    hashMap1.put(onlCgformField.getDbFieldName(), Integer.valueOf(1));
                HashMap<Object, Object> hashMap2 = new HashMap<>(5);
                for (OnlCgformField onlCgformField : paramList)
                    hashMap2.put(onlCgformField.getDbFieldName(), Integer.valueOf(1));
                ArrayList<String> arrayList1 = new ArrayList();
                ArrayList<String> arrayList2 = new ArrayList();
                for (String str : hashMap2.keySet()) {
                    if (hashMap1.get(str) == null) {
                        arrayList2.add(str);
                        continue;
                    }
                    arrayList1.add(str);
                }
                ArrayList<String> arrayList3 = new ArrayList();
                for (String str : hashMap1.keySet()) {
                    if (hashMap2.get(str) == null)
                        arrayList3.add(str);
                }
                if (arrayList3.size() > 0)
                    for (OnlCgformField onlCgformField : list1) {
                        if (arrayList3.contains(onlCgformField.getDbFieldName()))
                            this.fieldService.removeById(onlCgformField.getId());
                    }
                if (arrayList2.size() > 0)
                    for (OnlCgformField onlCgformField : paramList) {
                        if (arrayList2.contains(onlCgformField.getDbFieldName())) {
                            OnlCgformField onlCgformField1 = new OnlCgformField();
                            onlCgformField1.setCgformHeadId(onlCgformHead.getId());
                            a(onlCgformField, onlCgformField1);
                            this.fieldService.save(onlCgformField1);
                        }
                    }
                if (arrayList1.size() > 0)
                    for (String str : arrayList1)
                        b(str, paramList, list1);
            }
    }

    private void b(String paramString, List<OnlCgformField> paramList1, List<OnlCgformField> paramList2) {
        OnlCgformField onlCgformField1 = null;
        for (OnlCgformField onlCgformField : paramList1) {
            if (paramString.equals(onlCgformField.getDbFieldName()))
                onlCgformField1 = onlCgformField;
        }
        OnlCgformField onlCgformField2 = null;
        for (OnlCgformField onlCgformField : paramList2) {
            if (paramString.equals(onlCgformField.getDbFieldName()))
                onlCgformField2 = onlCgformField;
        }
        if (onlCgformField1 != null && onlCgformField2 != null) {
            boolean bool = false;
            if (!onlCgformField1.getDbType().equals(onlCgformField2.getDbType())) {
                onlCgformField2.setDbType(onlCgformField1.getDbType());
                bool = true;
            }
            if (onlCgformField1.getDbDefaultVal() != null && !onlCgformField1.getDbDefaultVal().equals(onlCgformField2.getDbDefaultVal())) {
                onlCgformField2.setDbDefaultVal(onlCgformField1.getDbDefaultVal());
                bool = true;
            }
            if (!onlCgformField1.getDbLength().equals(onlCgformField2.getDbLength())) {
                onlCgformField2.setDbLength(onlCgformField1.getDbLength());
                bool = true;
            }
            if (onlCgformField1.getDbIsNull() != onlCgformField2.getDbIsNull()) {
                onlCgformField2.setDbIsNull(onlCgformField1.getDbIsNull());
                bool = true;
            }
            if (bool)
                this.fieldService.updateById(onlCgformField2);
        }
    }

    private void a(OnlCgformField paramOnlCgformField1, OnlCgformField paramOnlCgformField2) {
        paramOnlCgformField2.setDbDefaultVal(paramOnlCgformField1.getDbDefaultVal());
        paramOnlCgformField2.setDbFieldName(paramOnlCgformField1.getDbFieldName());
        paramOnlCgformField2.setDbFieldNameOld(paramOnlCgformField1.getDbFieldNameOld());
        paramOnlCgformField2.setDbFieldTxt(paramOnlCgformField1.getDbFieldTxt());
        paramOnlCgformField2.setDbIsKey(paramOnlCgformField1.getDbIsKey());
        paramOnlCgformField2.setDbIsNull(paramOnlCgformField1.getDbIsNull());
        paramOnlCgformField2.setDbLength(paramOnlCgformField1.getDbLength());
        paramOnlCgformField2.setDbPointLength(paramOnlCgformField1.getDbPointLength());
        paramOnlCgformField2.setDbType(paramOnlCgformField1.getDbType());
        paramOnlCgformField2.setDictField(paramOnlCgformField1.getDictField());
        paramOnlCgformField2.setDictTable(paramOnlCgformField1.getDictTable());
        paramOnlCgformField2.setDictText(paramOnlCgformField1.getDictText());
        paramOnlCgformField2.setFieldExtendJson(paramOnlCgformField1.getFieldExtendJson());
        paramOnlCgformField2.setFieldHref(paramOnlCgformField1.getFieldHref());
        paramOnlCgformField2.setFieldLength(paramOnlCgformField1.getFieldLength());
        paramOnlCgformField2.setFieldMustInput(paramOnlCgformField1.getFieldMustInput());
        paramOnlCgformField2.setFieldShowType(paramOnlCgformField1.getFieldShowType());
        paramOnlCgformField2.setFieldValidType(paramOnlCgformField1.getFieldValidType());
        paramOnlCgformField2.setFieldDefaultValue(paramOnlCgformField1.getFieldDefaultValue());
        paramOnlCgformField2.setIsQuery(paramOnlCgformField1.getIsQuery());
        paramOnlCgformField2.setIsShowForm(paramOnlCgformField1.getIsShowForm());
        paramOnlCgformField2.setIsShowList(paramOnlCgformField1.getIsShowList());
        paramOnlCgformField2.setMainField(paramOnlCgformField1.getMainField());
        paramOnlCgformField2.setMainTable(paramOnlCgformField1.getMainTable());
        paramOnlCgformField2.setOrderNum(paramOnlCgformField1.getOrderNum());
        paramOnlCgformField2.setQueryMode(paramOnlCgformField1.getQueryMode());
        paramOnlCgformField2.setIsReadOnly(paramOnlCgformField1.getIsReadOnly());
        paramOnlCgformField2.setSortFlag(paramOnlCgformField1.getSortFlag());
        paramOnlCgformField2.setQueryDefVal(paramOnlCgformField1.getQueryDefVal());
        paramOnlCgformField2.setQueryConfigFlag(paramOnlCgformField1.getQueryConfigFlag());
        paramOnlCgformField2.setQueryDictField(paramOnlCgformField1.getQueryDictField());
        paramOnlCgformField2.setQueryDictTable(paramOnlCgformField1.getQueryDictTable());
        paramOnlCgformField2.setQueryDictText(paramOnlCgformField1.getQueryDictText());
        paramOnlCgformField2.setQueryMustInput(paramOnlCgformField1.getQueryMustInput());
        paramOnlCgformField2.setQueryShowType(paramOnlCgformField1.getQueryShowType());
        paramOnlCgformField2.setQueryValidType(paramOnlCgformField1.getQueryValidType());
        paramOnlCgformField2.setConverter(paramOnlCgformField1.getConverter());
        paramOnlCgformField2.setDbIsPersist(paramOnlCgformField1.getDbIsPersist());
    }

    private void a(OnlCgformField paramOnlCgformField) {
        if ("Text".equals(paramOnlCgformField.getDbType()) || "Blob".equals(paramOnlCgformField.getDbType())) {
            paramOnlCgformField.setDbLength(Integer.valueOf(0));
            paramOnlCgformField.setDbPointLength(Integer.valueOf(0));
        }
    }

    private String d(String paramString1, String paramString2) {
        List<String> list = ((OnlCgformHeadMapper) this.baseMapper).queryAllCopyTableName(paramString1);
        int i = 0;
        if (list != null || list.size() > 0)
            for (byte b1 = 0; b1 < list.size(); b1++) {
                String str = list.get(b1);
                int j = Integer.parseInt(str.split("\\$")[1]);
                if (j > i)
                    i = j;
            }
        return paramString2 + "$" + ++i;
    }

    @Transactional(rollbackFor = {Exception.class})
    public String deleteDataByCode(String cgformCode, String dataIds) {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, cgformCode));
        if (onlCgformHead == null)
            throw new JeecgBootException("实体不存在");
        String str = onlCgformHead.getTableName();
        try {
            if (dataIds.indexOf(",") > 0) {
                this.onlCgformFieldService.deleteAutoListById(str, dataIds);
            } else {
                deleteOneTableInfo(onlCgformHead.getId(), dataIds);
            }
        } catch (Exception exception) {
            a.error("OnlCgformApiController.formEdit()发生异常：" + exception.getMessage(), exception);
            throw new JeecgBootException("删除失败：" + exception.getMessage());
        }
        return str;
    }

    public JSONObject queryAllDataByTableNameForDesform(String tableName, String dataIds) throws AException {
        JSONObject jSONObject1 = new JSONObject();
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, tableName);
        OnlCgformHead onlCgformHead = (OnlCgformHead) getOne((Wrapper) lambdaQueryWrapper);
        if (onlCgformHead == null)
            throw new JeecgBootException("表单数据不存在！");
        Map<String, Object> map = queryManyFormData(onlCgformHead.getId(), dataIds);
        if (map == null)
            throw new JeecgBootException("表单数据查询失败！");
        JSONObject jSONObject2 = JSON.parseObject(JSON.toJSONString(map));
        String str = onlCgformHead.getSubTableStr();
        if (oConvertUtils.isNotEmpty(str)) {
            ArrayList arrayList = new ArrayList(Arrays.asList((Object[]) str.split(",")));
            LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
            lambdaQueryWrapper1.in(OnlCgformHead::getTableName, arrayList);
            List list = list((Wrapper) lambdaQueryWrapper1);
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            for (OnlCgformHead onlCgformHead1 : list) {
                JSONArray jSONArray = jSONObject2.getJSONArray(onlCgformHead1.getTableName());
                if (jSONArray != null && jSONArray.size() > 0)
                    if (0 == onlCgformHead1.getRelationType().intValue()) {
                        jSONObject3.put(onlCgformHead1.getTableName(), jSONArray);
                    } else {
                        JSONObject jSONObject = jSONArray.getJSONObject(0);
                        jSONObject4.put(onlCgformHead1.getTableName(), jSONObject);
                    }
                jSONObject2.remove(onlCgformHead1.getTableName());
            }
            jSONObject1.put("one2one", jSONObject4);
            jSONObject1.put("one2many", jSONObject3);
        }
        jSONObject1.put("main", jSONObject2);
        return jSONObject1;
    }

    @AutoLowApp(action = LowAppAopEnum.COPY, bizType = "cgform")
    public OnlCgformHead copyOnlineTable(String id, String tableName) {
        LambdaQueryWrapper lambdaQueryWrapper1 = new LambdaQueryWrapper();
        lambdaQueryWrapper1.eq(OnlCgformHead::getTableName, tableName);
        Long long_ = ((OnlCgformHeadMapper) this.baseMapper).selectCount((Wrapper) lambdaQueryWrapper1);
        if (long_ != null && long_.longValue() >= 1L)
            throw new JeecgBootException("表名已经存在!");
        OnlCgformHead onlCgformHead1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectById(id);
        if (onlCgformHead1 == null)
            throw new JeecgBootException("表不存在!");
        OnlCgformHead onlCgformHead2 = new OnlCgformHead();
        BeanUtils.copyProperties(onlCgformHead1, onlCgformHead2);
        String str = bConstant.a();
        onlCgformHead2.setId(str);
        onlCgformHead2.setSubTableStr(null);
        onlCgformHead2.setTableName(tableName);
        onlCgformHead2.setTableVersion(Integer.valueOf(1));
        onlCgformHead2.setIsDbSynch("N");
        onlCgformHead2.setCreateBy(null);
        onlCgformHead2.setCreateTime(null);
        onlCgformHead2.setUpdateBy(null);
        onlCgformHead2.setUpdateTime(null);
        LambdaQueryWrapper lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(OnlCgformField::getCgformHeadId, id);
        List list1 = this.fieldService.list((Wrapper) lambdaQueryWrapper2);
        ArrayList<OnlCgformField> arrayList = new ArrayList();
        if (list1 != null && list1.size() > 0)
            for (OnlCgformField onlCgformField1 : list1) {
                OnlCgformField onlCgformField2 = new OnlCgformField();
                BeanUtils.copyProperties(onlCgformField1, onlCgformField2);
                onlCgformField2.setCgformHeadId(str);
                onlCgformField2.setMainField(null);
                onlCgformField2.setMainTable(null);
                onlCgformField2.setId(null);
                onlCgformField2.setCreateBy(null);
                onlCgformField2.setCreateTime(null);
                onlCgformField2.setUpdateBy(null);
                onlCgformField2.setUpdateTime(null);
                arrayList.add(onlCgformField2);
            }
        LambdaQueryWrapper lambdaQueryWrapper3 = new LambdaQueryWrapper();
        lambdaQueryWrapper3.eq(OnlCgformIndex::getCgformHeadId, id);
        List list2 = this.indexService.list((Wrapper) lambdaQueryWrapper3);
        ArrayList<OnlCgformIndex> arrayList1 = new ArrayList();
        if (list2 != null && list2.size() > 0)
            for (OnlCgformIndex onlCgformIndex1 : list2) {
                OnlCgformIndex onlCgformIndex2 = new OnlCgformIndex();
                BeanUtils.copyProperties(onlCgformIndex1, onlCgformIndex2);
                onlCgformIndex2.setCgformHeadId(str);
                onlCgformIndex2.setId(null);
                onlCgformIndex2.setCreateBy(null);
                onlCgformIndex2.setCreateTime(null);
                onlCgformIndex2.setUpdateBy(null);
                onlCgformIndex2.setUpdateTime(null);
                arrayList1.add(onlCgformIndex2);
            }
        save(onlCgformHead2);
        this.fieldService.saveBatch(arrayList);
        this.indexService.saveBatch(arrayList1);
        return onlCgformHead2;
    }

    public OnlCgformHead getTable(String code) throws AException {
        OnlCgformHead onlCgformHead = (OnlCgformHead) getById(code);
        if (onlCgformHead == null) {
            LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper) (new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, code);
            onlCgformHead = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne((Wrapper) lambdaQueryWrapper);
        }
        if (onlCgformHead == null)
       throws new AException("online表[" + code + "]不存在");
        return onlCgformHead;
    }

    private bConstant getOnlineDataBaseConfig() {
        if (oConvertUtils.isEmpty(this.onlineDatasource))
            return this.dataBaseConfig;
        DataSourceProperty dataSourceProperty = CommonUtils.getDataSourceProperty(this.onlineDatasource);
        if (dataSourceProperty == null) {
            a.error("jeecg.online.datasource配置错误,获取不到数据源返回master");
            return this.dataBaseConfig;
        }
        bConstant b1 = new bConstant();
        b1.setDriverClassName(dataSourceProperty.getDriverClassName());
        b1.setPassword(dataSourceProperty.getPassword());
        b1.setUsername(dataSourceProperty.getUsername());
        b1.setUrl(dataSourceProperty.getUrl());
        b1.setDmDataBaseConfig(new c());
        return b1;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\a\d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
