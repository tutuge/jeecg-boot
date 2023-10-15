package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.TemplateException;
import jakarta.annotation.Resource;
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
import org.jeecg.modules.online.cgform.b1.bLinkConstant;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaImportInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.cgform.enhance.impl.http.cgformEnhanceJavaHttpImpl;
import org.jeecg.modules.online.cgform.enhance.impl.http.cgformEnhanceJavaListHttpImpl;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.mapper.*;
import org.jeecg.modules.online.cgform.model.i;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.config.bAttribute.Attribute;
import org.jeecg.modules.online.config.bAttribute.bDataBaseConfig;
import org.jeecg.modules.online.config.bAttribute.cDataBaseConfig;
import org.jeecg.modules.online.config.dUtil.dFtl;
import org.jeecg.modules.online.config.dUtil.eDbTableHandle;
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
    @Resource
    private OnlCgformButtonMapper onlCgformButtonMapper;
    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;
    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;
    @Autowired
    private bDataBaseConfig dataBaseConfig;
    @Autowired
    private IOnlAuthPageService onlAuthPageService;
    @Autowired
    private IOnlAuthDataService onlAuthDataService;
    @Autowired
    private IOnlAuthRelationService onlAuthRelationService;
    @Autowired
    private cgformEnhanceJavaHttpImpl cgformEnhanceJavaHttp;
    @Autowired
    private cgformEnhanceJavaListHttpImpl cgformEnhanceJavaListHttp;
    @Value("${jeecg.online.datasource:}")
    private String onlineDatasource;
    @Lazy
    @Autowired
    private ISysBaseAPI sysBaseApi;

    public d() {
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<?> addAll(org.jeecg.modules.online.cgform.model.a model) {
        String var2 = UUID.randomUUID().toString().replace("-", "");
        OnlCgformHead var3 = model.getHead();
        List<OnlCgformField> var4 = model.getFields();
        List<OnlCgformIndex> var5 = model.getIndexs();
        var3.setId(var2);
        boolean var6 = false;

        for (int var7 = 0; var7 < var4.size(); ++var7) {
            OnlCgformField var8 = (OnlCgformField) var4.get(var7);
            var8.setId((String) null);
            var8.setCgformHeadId(var2);
            if (var8.getOrderNum() == null) {
                var8.setOrderNum(var7);
            }

            if (oConvertUtils.isNotEmpty(var8.getMainTable()) && oConvertUtils.isNotEmpty(var8.getMainField())) {
                var6 = true;
            }

            this.a(var8);
            if (var8.getDbIsPersist() == null) {
                var8.setDbIsPersist(bLinkConstant.b);
            }
        }

        Iterator var9 = var5.iterator();

        while (var9.hasNext()) {
            OnlCgformIndex var10 = (OnlCgformIndex) var9.next();
            var10.setId(null);
            var10.setCgformHeadId(var2);
            var10.setIsDbSynch("N");
            var10.setDelFlag(CommonConstant.DEL_FLAG_0);
        }

        var3.setIsDbSynch("N");
        var3.setQueryMode("single");
        var3.setTableVersion(1);
        var3.setCopyType(0);
        if (var3.getTableType() == 3 && var3.getTabOrderNum() == null) {
            var3.setTabOrderNum(1);
        }

        super.save(var3);
        this.fieldService.saveBatch(var4);
        this.indexService.saveBatch(var5);
        this.a(var3, var4);
        if (var3.getTableType() == 3 && var6) {
            this.onlCgformFieldService.clearCacheOnlineConfig();
        }

        return Result.ok("添加成功");
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<?> editAll(org.jeecg.modules.online.cgform.model.a model) {
        OnlCgformHead var2 = model.getHead();
        OnlCgformHead var3 = (OnlCgformHead) super.getById(var2.getId());
        if (var3 == null) {
            return Result.error("未找到对应实体");
        } else {
            String var4 = var3.getIsDbSynch();
            if (bConstant.getId(var3, var2)) {
                var4 = "N";
            }

            Integer var5 = var3.getTableVersion();
            if (var5 == null) {
                var5 = 1;
            }

            var2.setTableVersion(var5 + 1);
            List var6 = model.getFields();
            List<OnlCgformIndex> var7 = model.getIndexs();
            ArrayList var8 = new ArrayList<>();
            ArrayList var9 = new ArrayList<>();
            Iterator var10 = var6.iterator();

            while (var10.hasNext()) {
                OnlCgformField var11 = (OnlCgformField) var10.next();
                String var12 = String.valueOf(var11.getId());
                this.a(var11);
                if (var12.length() == 32) {
                    var9.add(var11);
                } else {
                    String var13 = "_pk";
                    if (!var13.equals(var12)) {
                        var11.setId(null);
                        var11.setCgformHeadId(var2.getId());
                        var8.add(var11);
                    }
                }

                if (var11.getDbIsPersist() == null) {
                    var11.setDbIsPersist(bLinkConstant.b);
                }
            }

            if (!var8.isEmpty() && this.a(var8)) {
                var4 = "N";
            }

            int var18 = 0;

            UpdateWrapper<OnlCgformField> var15;
            Iterator var19;
            OnlCgformField onlCgformField;
            for (var19 = var9.iterator(); var19.hasNext(); this.fieldService.update(onlCgformField, var15)) {
                onlCgformField = (OnlCgformField) var19.next();
                OnlCgformField var23 = this.fieldService.getById(onlCgformField.getId());
                this.a(var23.getMainTable(), var2.getTableName());
                boolean var14 = bConstant.getId(var23, onlCgformField);
                if (var14) {
                    var4 = "N";
                }

                if ((var23.getOrderNum() == null ? 0 : var23.getOrderNum()) > var18) {
                    var18 = var23.getOrderNum();
                }

                if ("Y".equals(var3.getIsDbSynch()) && !onlCgformField.getDbFieldName().equals(var23.getDbFieldName())) {
                    onlCgformField.setDbFieldNameOld(var23.getDbFieldName());
                }

                var15 = new UpdateWrapper<>();
                var15.lambda().eq(OnlCgformField::getId, onlCgformField.getId());
                if (onlCgformField.getFieldValidType() == null) {
                    var15.lambda().set(OnlCgformField::getFieldValidType, "");
                }
            }

            for (var19 = var8.iterator(); var19.hasNext(); this.fieldService.save(onlCgformField)) {
                onlCgformField = (OnlCgformField) var19.next();
                if (onlCgformField.getOrderNum() == null) {
                    ++var18;
                    onlCgformField.setOrderNum(var18);
                }
            }

            List var20 = this.indexService.getCgformIndexsByCgformId(var2.getId());
            ArrayList var22 = new ArrayList<>();
            ArrayList var24 = new ArrayList<>();
            Iterator<OnlCgformIndex> var25 = var7.iterator();

            String var16;
            OnlCgformIndex var26;
            while (var25.hasNext()) {
                var26 = var25.next();
                var16 = String.valueOf(var26.getId());
                if (var16.length() == 32) {
                    var24.add(var26);
                } else {
                    var26.setId(null);
                    var26.setIsDbSynch("N");
                    var26.setDelFlag(CommonConstant.DEL_FLAG_0);
                    var26.setCgformHeadId(var2.getId());
                    var22.add(var26);
                }
            }

            var25 = var20.iterator();

            while (var25.hasNext()) {
                var26 = var25.next();
                var16 = String.valueOf(var26.getId());
                if (var16.length() == 32) {
                    var24.add(var26);
                } else {
                    var26.setId((String) null);
                    var26.setIsDbSynch("N");
                    var26.setDelFlag(CommonConstant.DEL_FLAG_0);
                    var26.setCgformHeadId(var2.getId());
                    var22.add(var26);
                }
            }

            if (var22.size() > 0) {
                var4 = "N";
                this.indexService.saveBatch(var22);
            }

            for (var25 = var24.iterator(); var25.hasNext(); this.indexService.updateById(var26)) {
                var26 = (OnlCgformIndex) var25.next();
                OnlCgformIndex var30 = (OnlCgformIndex) this.indexService.getById(var26.getId());
                boolean var17 = bConstant.getId(var30, var26);
                if (var17) {
                    var4 = "N";
                    var26.setIsDbSynch("N");
                }
            }

            if (model.getDeleteFieldIds().size() > 0) {
                List var27 = model.getDeleteFieldIds();
                Iterator var29 = var27.iterator();

                while (var29.hasNext()) {
                    var16 = (String) var29.next();
                    OnlCgformField var31 = this.fieldService.getById(var16);
                    if (var31 != null) {
                        if (bLinkConstant.b.equals(var31.getDbIsPersist())) {
                            var4 = "N";
                        }

                        this.a(var31.getMainTable(), var2.getTableName());
                        this.fieldService.removeById(var16);
                    }
                }
            }

            var2.setIsDbSynch(var4);
            super.updateById(var2);
            this.a(var2, var6);
            this.b(var2, var6);
            return Result.ok("全部修改成功");
        }
    }

    private boolean a(List<OnlCgformField> var1) {
        if (var1 != null && var1.size() != 0) {
            boolean var2 = false;
            Iterator var3 = var1.iterator();

            while (var3.hasNext()) {
                OnlCgformField var4 = (OnlCgformField) var3.next();
                if (bLinkConstant.b.equals(var4.getDbIsPersist())) {
                    var2 = true;
                    break;
                }
            }

            return var2;
        } else {
            return false;
        }
    }

    private void a(String var1, String var2) {
        if (oConvertUtils.isNotEmpty(var1)) {
            LambdaQueryWrapper var3 = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var1);
            OnlCgformHead var4 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne(var3);
            if (var4 != null && oConvertUtils.isNotEmpty(var4.getSubTableStr())) {
                String var5 = var4.getSubTableStr();
                String[] var6 = var5.split(",");
                ArrayList var7 = new ArrayList<>();
                String[] var8 = var6;
                int var9 = var6.length;

                for (int var10 = 0; var10 < var9; ++var10) {
                    String var11 = var8[var10];
                    if (!var11.equals(var2)) {
                        var7.add(var11);
                    }
                }

                var4.setSubTableStr(String.join(",", var7));
                ((OnlCgformHeadMapper) this.baseMapper).updateById(var4);
            }
        }

    }

    public void doDbSynch(String code, String synMethod) throws HibernateException, IOException, TemplateException, SQLException, AException {
        OnlCgformHead var3 = this.getById(code);
        if (var3 == null) {
            throw new AException("实体配置不存在");
        } else {
            String var4 = var3.getTableName();
            LambdaQueryWrapper<OnlCgformField> var5 = Wrappers.lambdaQuery(OnlCgformField.class)
                    .eq(OnlCgformField::getCgformHeadId, code)
                    .eq(OnlCgformField::getDbIsPersist, bLinkConstant.b)
                    .orderByAsc(OnlCgformField::getOrderNum);
            List<OnlCgformField> var6 = this.fieldService.list(var5);
            Attribute var7 = new Attribute();
            var7.setTableName(var4);
            var7.setJformPkType(var3.getIdType());
            var7.setJformPkSequence(var3.getIdSequence());
            var7.setContent(var3.getTableTxt());
            var7.setColumns(var6);
            bDataBaseConfig var8 = this.getOnlineDataBaseConfig();
            var7.setDbConfig(var8);
            DbType var9 = eDbTableHandle.c(var8);
            if ("normal".equals(synMethod) && !var9.equals(DbType.SQLITE)) {
                long var23 = System.currentTimeMillis();
                boolean var12 = eDbTableHandle.a(var4, var8);
                if (var12) {
                    dFtl var13 = new dFtl(var8);
                    List var14 = var13.b(var7);
                    Iterator var15 = var14.iterator();

                    label94:
                    while (true) {
                        String var16;
                        do {
                            do {
                                if (!var15.hasNext()) {
                                    List<OnlCgformIndex> var24 = this.indexService.list(Wrappers.lambdaQuery(OnlCgformIndex.class).eq(OnlCgformIndex::getCgformHeadId, code));
                                    Iterator<OnlCgformIndex> var25 = var24.iterator();

                                    while (true) {
                                        OnlCgformIndex var26;
                                        do {
                                            if (!var25.hasNext()) {
                                                break label94;
                                            }

                                            var26 = var25.next();
                                        } while (!"N".equals(var26.getIsDbSynch()) && !CommonConstant.DEL_FLAG_1.equals(var26.getDelFlag()));

                                        String var27 = var13.b(var26.getIndexName(), var4);
                                        if (this.indexService.isExistIndex(var27)) {
                                            String var28 = var13.a(var26.getIndexName(), var4);

                                            try {
                                                ((OnlCgformHeadMapper) this.baseMapper).executeDDL(var28);
                                                if (CommonConstant.DEL_FLAG_1.equals(var26.getDelFlag())) {
                                                    this.indexService.removeById(var26.getId());
                                                }
                                            } catch (Exception var22) {
                                                a.error("删除表【" + var4 + "】索引(" + var26.getIndexName() + ")失败!", var22);
                                            }
                                        } else if (CommonConstant.DEL_FLAG_1.equals(var26.getDelFlag())) {
                                            this.indexService.removeById(var26.getId());
                                        }
                                    }
                                }

                                var16 = (String) var15.next();
                            } while (oConvertUtils.isEmpty(var16));
                        } while (oConvertUtils.isEmpty(var16.trim()));

                        String[] var17 = var16.split(";");
                        if (var17 != null && var17.length > 1) {
                        }

                        String[] var18 = var17;
                        int var19 = var17.length;

                        for (int var20 = 0; var20 < var19; ++var20) {
                            String var21 = var18[var20];
                            if (!oConvertUtils.isEmpty(var21) && !oConvertUtils.isEmpty(var21.trim())) {
                                this.baseMapper.executeDDL(var21);
                            }
                        }
                    }
                } else {
                    dFtl.a(var7);
                }
            } else if ("force".equals(synMethod) || var9.equals(DbType.SQLITE)) {
                DbTableHandleI var10 = eDbTableHandle.getTableHandle();
                String var11 = var10.dropTableSQL(var4);
                this.baseMapper.executeDDL(var11);
                dFtl.a(var7);
            }

            this.indexService.createIndex(code, eDbTableHandle.getDatabaseType(), var4);
            var3.setIsDbSynch("Y");
            if (var3.getTableVersion() == 1) {
                var3.setTableVersion(2);
            }

            this.updateById(var3);
        }
    }

    public void deleteRecordAndTable(String id) throws AException, SQLException {
        OnlCgformHead var2 = this.getById(id);
        if (var2 == null) {
            throw new AException("实体配置不存在");
        } else {
            long var3 = System.currentTimeMillis();
            boolean var5 = eDbTableHandle.a(var2.getTableName());
            if (var5) {
                String var6 = eDbTableHandle.getTableHandle().dropTableSQL(var2.getTableName());
                this.baseMapper.executeDDL(var6);
            }

            this.deleteRecord(id);
        }
    }

    public void deleteRecord(String id) throws AException, SQLException {
        OnlCgformHead var2 = this.getById(id);
        if (var2 == null) {
            throw new AException("实体配置不存在");
        } else {
            LambdaQueryWrapper<OnlCgformHead> var3 = Wrappers.lambdaQuery(OnlCgformHead.class)
                    .eq(OnlCgformHead::getPhysicId, id);
            List<OnlCgformHead> var4 = this.baseMapper.selectList(var3);
            if (var4 != null && !var4.isEmpty()) {
                Iterator var5 = var4.iterator();

                while (var5.hasNext()) {
                    OnlCgformHead var6 = (OnlCgformHead) var5.next();
                    this.a(var6.getId());
                }
            }

            this.a(var2);
            this.a(id);
            if (var2.getTableType() == 3) {
                this.onlCgformFieldService.clearCacheOnlineConfig();
            }

        }
    }

    private void a(String var1) {
        this.baseMapper.deleteById(var1);
        LambdaQueryWrapper<OnlCgformField> var2 = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, var1);
        this.fieldService.remove(var2);
        LambdaQueryWrapper<OnlCgformIndex> var3 = Wrappers.lambdaQuery(OnlCgformIndex.class)
                .eq(OnlCgformIndex::getCgformHeadId, var1);
        this.indexService.remove(var3);
        LambdaQueryWrapper<OnlAuthRelation> var4 = Wrappers.lambdaQuery(OnlAuthRelation.class)
                .eq(OnlAuthRelation::getCgformId, var1);
        this.onlAuthRelationService.remove(var4);
        LambdaQueryWrapper<OnlAuthData> var5 = Wrappers.lambdaQuery(OnlAuthData.class)
                .eq(OnlAuthData::getCgformId, var1);
        this.onlAuthDataService.remove(var5);
        LambdaQueryWrapper<OnlAuthPage> var6 = Wrappers.lambdaQuery(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, var1);
        this.onlAuthPageService.remove(var6);
    }

    private void a(OnlCgformHead var1) {
        if (var1.getTableType() == 3) {
            LambdaQueryWrapper<OnlCgformField> var2 = Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var1.getId());
            List var3 = this.fieldService.list(var2);
            String var4 = null;
            Iterator var5 = var3.iterator();

            while (var5.hasNext()) {
                OnlCgformField var6 = (OnlCgformField) var5.next();
                var4 = var6.getMainTable();
                if (oConvertUtils.isNotEmpty(var4)) {
                    break;
                }
            }

            if (oConvertUtils.isNotEmpty(var4)) {
                OnlCgformHead var8 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var4));
                if (var8 != null) {
                    String var9 = var8.getSubTableStr();
                    if (oConvertUtils.isNotEmpty(var9)) {
                        List<String> var7 = Arrays.asList(var9.split(",")).stream().collect(Collectors.toList());
                        var7.remove(var1.getTableName());
                        var8.setSubTableStr(String.join(",", var7));
                        this.baseMapper.updateById(var8);
                    }
                }
            }
        }

    }

    public List<Map<String, Object>> queryListData(String sql) {
        return this.baseMapper.queryList(sql);
    }

    public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
    }

    public OnlCgformEnhanceJs queryEnhance(String code, String type) {
        return this.onlCgformEnhanceJsMapper.selectOne(Wrappers.lambdaQuery(OnlCgformEnhanceJs.class)
                .eq(OnlCgformEnhanceJs::getCgJsType, type).eq(OnlCgformEnhanceJs::getCgformHeadId, code));
    }

    public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
        this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
    }

    public OnlCgformEnhanceSql queryEnhanceSql(String formId, String buttonCode) {
        return this.onlCgformEnhanceSqlMapper.selectOne(Wrappers.lambdaQuery(OnlCgformEnhanceSql.class)
                .eq(OnlCgformEnhanceSql::getCgformHeadId, formId)
                .eq(OnlCgformEnhanceSql::getButtonCode, buttonCode));
    }

    public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper var2 = Wrappers.lambdaQuery(OnlCgformEnhanceJava.class)
                .eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode())
                .eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId())
                .eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType())
                .eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        return this.onlCgformEnhanceJavaMapper.selectOne(var2);
    }

    public List<OnlCgformButton> queryButtonList(String code, boolean isListButton) {
        LambdaQueryWrapper<OnlCgformButton> var3 = Wrappers.lambdaQuery(OnlCgformButton.class)
                .eq(OnlCgformButton::getButtonStatus, "1")
                .eq(OnlCgformButton::getCgformHeadId, code);
        if (isListButton) {
            var3.in(OnlCgformButton::getButtonStyle, new Object[]{"link", "button"});
        } else {
            var3.eq(OnlCgformButton::getButtonStyle, "form");
        }

        var3.orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(var3);
    }

    public List<OnlCgformButton> queryButtonList(String code) {
        LambdaQueryWrapper<OnlCgformButton> var2 = Wrappers.lambdaQuery(OnlCgformButton.class)
                .eq(OnlCgformButton::getButtonStatus, "1")
                .eq(OnlCgformButton::getCgformHeadId, code)
                .orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(var2);
    }

    public List<String> queryOnlinetables() {
        return this.baseMapper.queryOnlinetables();
    }

    @AutoLowApp(
            action = LowAppAopEnum.CGFORM_DB_IMPORT,
            bizType = "cgform"
    )
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void saveDbTable2Online(String tbname) {
        OnlCgformHead var2 = new OnlCgformHead();
        var2.setTableType(1);
        var2.setIsCheckbox("Y");
        var2.setIsDbSynch("Y");
        var2.setIsTree("N");
        var2.setIsPage("Y");
        var2.setQueryMode("group");
        var2.setTableName(tbname.toLowerCase());
        var2.setTableTxt(tbname);
        var2.setTableVersion(1);
        var2.setFormTemplate("1");
        var2.setCopyType(0);
        var2.setIsDesForm("N");
        var2.setScroll(1);
        var2.setThemeTemplate("normal");
        String var3 = UUIDGenerator.generate();
        var2.setId(var3);
        ArrayList var4 = new ArrayList<>();

        try {
            List var5 = DbReadTableUtil.readOriginalTableColumn(tbname);

            for (int var6 = 0; var6 < var5.size(); ++var6) {
                ColumnVo var7 = (ColumnVo) var5.get(var6);
                String var8 = var7.getFieldDbName();
                OnlCgformField var9 = new OnlCgformField();
                var9.setCgformHeadId(var3);
                var9.setDbFieldNameOld(var7.getFieldDbName().toLowerCase());
                var9.setDbFieldName(var7.getFieldDbName().toLowerCase());
                if (oConvertUtils.isNotEmpty(var7.getFiledComment())) {
                    var9.setDbFieldTxt(var7.getFiledComment());
                } else {
                    var9.setDbFieldTxt(var7.getFieldName());
                }

                var9.setDbIsKey(0);
                var9.setIsShowForm(1);
                var9.setIsQuery(0);
                var9.setFieldMustInput("0");
                var9.setIsShowList(1);
                var9.setOrderNum(var6 + 1);
                var9.setQueryMode("single");
                var9.setDbLength(oConvertUtils.getInt(var7.getPrecision()));
                var9.setFieldLength(120);
                var9.setDbPointLength(oConvertUtils.getInt(var7.getScale()));
                var9.setFieldShowType("text");
                var9.setDbIsNull("Y".equals(var7.getNullable()) ? 1 : 0);
                var9.setIsReadOnly(0);
                if ("id".equalsIgnoreCase(var8)) {
                    String[] var10 = new String[]{"java.lang.Integer", "java.lang.Long"};
                    String var11 = var7.getFieldType();
                    if (Arrays.asList(var10).contains(var11)) {
                        var2.setIdType("NATIVE");
                    } else {
                        var2.setIdType("UUID");
                    }

                    var9.setDbIsKey(1);
                    var9.setIsShowForm(0);
                    var9.setIsShowList(0);
                    var9.setIsReadOnly(1);
                }

                if ("create_by".equalsIgnoreCase(var8) || "create_time".equalsIgnoreCase(var8) || "update_by".equalsIgnoreCase(var8) || "update_time".equalsIgnoreCase(var8) || "sys_org_code".equalsIgnoreCase(var8)) {
                    var9.setIsShowForm(0);
                    var9.setIsShowList(0);
                }

                if ("java.lang.Integer".equalsIgnoreCase(var7.getFieldType())) {
                    var9.setDbType("int");
                } else if ("java.lang.Long".equalsIgnoreCase(var7.getFieldType())) {
                    var9.setDbType("int");
                } else if ("java.util.Date".equalsIgnoreCase(var7.getFieldType())) {
                    if ("datetime".equals(var7.getFieldDbType())) {
                        var9.setDbType("Datetime");
                        var9.setFieldShowType("datetime");
                    } else {
                        var9.setDbType("Date");
                        var9.setFieldShowType("date");
                    }
                } else if (!"java.lang.Double".equalsIgnoreCase(var7.getFieldType()) && !"java.lang.Float".equalsIgnoreCase(var7.getFieldType())) {
                    if (!"java.math.BigDecimal".equalsIgnoreCase(var7.getFieldType()) && !"BigDecimal".equalsIgnoreCase(var7.getFieldType())) {
                        if (!"byte[]".equalsIgnoreCase(var7.getFieldType()) && !var7.getFieldType().contains("blob")) {
                            if ("java.lang.Object".equals(var7.getFieldType()) && ("text".equalsIgnoreCase(var7.getFieldDbType()) || "ntext".equalsIgnoreCase(var7.getFieldDbType()))) {
                                var9.setDbType("Text");
                                var9.setFieldShowType("textarea");
                            } else if ("java.lang.Object".equals(var7.getFieldType()) && "image".equalsIgnoreCase(var7.getFieldDbType())) {
                                var9.setDbType("Blob");
                            } else {
                                var9.setDbType("string");
                            }
                        } else {
                            var9.setDbType("Blob");
                            var7.setCharmaxLength((String) null);
                        }
                    } else {
                        var9.setDbType("BigDecimal");
                    }
                } else {
                    var9.setDbType("double");
                }

                if (oConvertUtils.isEmpty(var7.getPrecision()) && oConvertUtils.isNotEmpty(var7.getCharmaxLength())) {
                    if (Long.valueOf(var7.getCharmaxLength()) >= 3000L) {
                        var9.setDbType("Text");
                        var9.setFieldShowType("textarea");

                        try {
                            var9.setDbLength(Integer.valueOf(var7.getCharmaxLength()));
                        } catch (Exception var12) {
                            a.error(var12.getMessage(), var12);
                        }
                    } else {
                        var9.setDbLength(Integer.valueOf(var7.getCharmaxLength()));
                    }
                } else {
                    if (oConvertUtils.isNotEmpty(var7.getPrecision())) {
                        var9.setDbLength(Integer.valueOf(var7.getPrecision()));
                    } else if (var9.getDbType().equals("int")) {
                        var9.setDbLength(10);
                    }

                    if (oConvertUtils.isNotEmpty(var7.getScale())) {
                        var9.setDbPointLength(Integer.valueOf(var7.getScale()));
                    }
                }

                if (oConvertUtils.getInt(var7.getPrecision()) == -1 && oConvertUtils.getInt(var7.getScale()) == 0) {
                    var9.setDbType("Text");
                }

                if ("Blob".equals(var9.getDbType()) || "Text".equals(var9.getDbType()) || "Date".equals(var9.getDbType())) {
                    var9.setDbLength(0);
                    var9.setDbPointLength(0);
                }

                var9.setDbIsPersist(bLinkConstant.b);
                var4.add(var9);
            }
        } catch (Exception var13) {
            a.error(var13.getMessage(), var13);
        }

        if (oConvertUtils.isEmpty(var2.getFormCategory())) {
            var2.setFormCategory("bdfl_include");
        }

        this.save(var2);
        this.fieldService.saveBatch(var4);
    }

    private boolean b(String var1, String var2) {
        if (oConvertUtils.isEmpty(var2)) {
            return false;
        } else {
            String[] var3 = var2.split(",");
            String[] var4 = var3;
            int var5 = var3.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String var7 = var4[var6];
                if (var7.equalsIgnoreCase(var1)) {
                    return true;
                }
            }

            return false;
        }
    }

    private void a(OnlCgformHead var1, List<OnlCgformField> var2) {
        if (var1.getTableType() == 3) {
            var1 = this.baseMapper.selectById(var1.getId());

            for (int var3 = 0; var3 < var2.size(); ++var3) {
                OnlCgformField var4 = var2.get(var3);
                String var5 = var4.getMainTable();
                if (!oConvertUtils.isEmpty(var5)) {
                    OnlCgformHead var6 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var5));
                    if (var6 != null) {
                        String var7 = var6.getSubTableStr();
                        if (oConvertUtils.isEmpty(var7)) {
                            var7 = var1.getTableName();
                        } else if (!this.b(var1.getTableName(), var7)) {
                            ArrayList var8 = new ArrayList(Arrays.asList(var7.split(",")));

                            for (int var9 = 0; var9 < var8.size(); ++var9) {
                                String var10 = (String) var8.get(var9);
                                OnlCgformHead var11 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var10));
                                if (var11 != null && var1.getTabOrderNum() < oConvertUtils.getInt(var11.getTabOrderNum(), 0)) {
                                    var8.add(var9, var1.getTableName());
                                    break;
                                }
                            }

                            if (var8.indexOf(var1.getTableName()) < 0) {
                                var8.add(var1.getTableName());
                            }

                            var7 = String.join(",", var8);
                        }

                        var6.setSubTableStr(var7);
                        ((OnlCgformHeadMapper) this.baseMapper).updateById(var6);
                        break;
                    }
                }
            }
        } else {
            List var12 = this.baseMapper.selectList(Wrappers.lambdaQuery(OnlCgformHead.class)
                    .like(OnlCgformHead::getSubTableStr, var1.getTableName()));
            if (var12 != null && var12.size() > 0) {
                Iterator var13 = var12.iterator();

                while (var13.hasNext()) {
                    OnlCgformHead var14 = (OnlCgformHead) var13.next();
                    String var15 = var14.getSubTableStr();
                    if (var14.getSubTableStr().equals(var1.getTableName())) {
                        var15 = "";
                    } else if (var14.getSubTableStr().startsWith(var1.getTableName() + ",")) {
                        var15 = var15.replace(var1.getTableName() + ",", "");
                    } else if (var14.getSubTableStr().endsWith("," + var1.getTableName())) {
                        var15 = var15.replace("," + var1.getTableName(), "");
                    } else if (var14.getSubTableStr().indexOf("," + var1.getTableName() + ",") != -1) {
                        var15 = var15.replace("," + var1.getTableName() + ",", ",");
                    }

                    var14.setSubTableStr(var15);
                    ((OnlCgformHeadMapper) this.baseMapper).updateById(var14);
                }
            }
        }

    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public String saveManyFormData(String code, JSONObject json, String xAccessToken) throws AException, BusinessException {
        OnlCgformHead var4 = this.getTable(code);
        String var5 = "add";
        this.executeEnhanceJava(var5, "start", var4, json);
        String var6 = bConstant.f(var4.getTableName());
        if (var4.getTableType() == 2) {
            String var7 = var4.getSubTableStr();
            if (oConvertUtils.isNotEmpty(var7)) {
                String[] var8 = var7.split(",");
                String[] var9 = var8;
                int var10 = var8.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    String var12 = var9[var11];
                    JSONArray var13 = json.getJSONArray(var12);
                    if (var13 != null && var13.size() != 0) {
                        OnlCgformHead var14 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var12));
                        if (var14 != null) {
                            List var15 = this.fieldService.list(Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var14.getId()));
                            String var16 = "";
                            String var17 = null;
                            Iterator var18 = var15.iterator();

                            while (var18.hasNext()) {
                                OnlCgformField var19 = (OnlCgformField) var18.next();
                                if (!oConvertUtils.isEmpty(var19.getMainField())) {
                                    var16 = var19.getDbFieldName();
                                    String var20 = var19.getMainField();
                                    if (json.get(var20.toLowerCase()) != null) {
                                        var17 = json.getString(var20.toLowerCase());
                                    }

                                    if (json.get(var20.toUpperCase()) != null) {
                                        var17 = json.getString(var20.toUpperCase());
                                    }
                                }
                            }

                            for (int var21 = 0; var21 < var13.size(); ++var21) {
                                JSONObject var22 = var13.getJSONObject(var21);
                                if (var17 != null) {
                                    var22.put(var16, var17);
                                }

                                this.fieldService.saveFormData(var15, var12, var22);
                            }
                        }
                    }
                }
            }
        }

        if ("Y".equals(var4.getIsTree())) {
            this.fieldService.saveTreeFormData(var4.getId(), var6, json, var4.getTreeIdField(), var4.getTreeParentIdField());
        } else {
            this.fieldService.saveFormData(var4.getId(), var6, json, false);
        }

        this.executeEnhanceSql(var5, var4.getId(), json);
        this.executeEnhanceJava(var5, "end", var4, json);
        return var4.getTableName();
    }

    public Map<String, Object> querySubFormData(String table, String mainId) throws AException {
        new HashMap(5);
        OnlCgformHead var4 = this.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, table));
        if (var4 == null) {
            throw new AException("数据库子表[" + table + "]不存在");
        } else {
            List var5 = this.fieldService.queryFormFields(var4.getId(), false);
            String var6 = null;
            Iterator var7 = var5.iterator();

            while (var7.hasNext()) {
                OnlCgformField var8 = (OnlCgformField) var7.next();
                if (oConvertUtils.isNotEmpty(var8.getMainField())) {
                    var6 = var8.getDbFieldName();
                    break;
                }
            }

            List var9 = this.fieldService.querySubFormData(var5, table, var6, mainId);
            if (var9 != null && var9.size() == 0) {
                throw new AException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
            } else if (var9.size() > 1) {
                throw new AException("数据库子表[" + table + "]存在多条记录,主表ID为" + mainId);
            } else {
                Map var3 = (Map) var9.get(0);
                return var3;
            }
        }
    }

    public List<Map<String, Object>> queryManySubFormData(String table, String mainId) throws AException {
        OnlCgformHead var3 = this.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, table));
        if (var3 == null) {
            throw new AException("数据库子表[" + table + "]不存在");
        } else {
            List var4 = this.fieldService.queryFormFields(var3.getId(), false);
            if (var4 != null && var4.size() != 0) {
                String var5 = null;
                String var6 = null;
                String var7 = null;
                Iterator var8 = var4.iterator();

                OnlCgformField var9;
                while (var8.hasNext()) {
                    var9 = (OnlCgformField) var8.next();
                    if (oConvertUtils.isNotEmpty(var9.getMainField())) {
                        var5 = var9.getDbFieldName();
                        var6 = var9.getMainTable();
                        var7 = var9.getMainField();
                        break;
                    }
                }

                ArrayList var16 = new ArrayList<>();
                var9 = new OnlCgformField();
                var9.setDbFieldName(var7);
                var16.add(var9);
                Map var10 = this.fieldService.queryFormData(var16, var6, mainId);
                String var11 = oConvertUtils.getString(oConvertUtils.getString(var10.get(var7)), oConvertUtils.getString(var10.get(var7.toUpperCase())));
                List var12 = this.fieldService.querySubFormData(var4, table, var5, var11);
                if (var12 != null && var12.size() == 0) {
                    throw new AException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
                } else {
                    ArrayList var13 = new ArrayList(var12.size());
                    Iterator var14 = var12.iterator();

                    while (var14.hasNext()) {
                        Map var15 = (Map) var14.next();
                        var13.add(bConstant.getId(var15));
                    }

                    return var13;
                }
            } else {
                throw new AException("找不到子表字段，请确认配置是否正确!");
            }
        }
    }

    public Map<String, Object> queryManyFormData(String code, String id) throws AException {
        OnlCgformHead var3 = this.getTable(code);
        List var4 = this.fieldService.queryFormFields(var3.getId(), true);
        if (var4 != null && var4.size() != 0) {
            Map var5 = this.fieldService.queryFormData(var4, var3.getTableName(), id);
            if (var3.getTableType() == 2) {
                String var6 = var3.getSubTableStr();
                if (oConvertUtils.isNotEmpty(var6)) {
                    String[] var7 = var6.split(",");
                    String[] var8 = var7;
                    int var9 = var7.length;

                    for (int var10 = 0; var10 < var9; ++var10) {
                        String var11 = var8[var10];
                        OnlCgformHead var12 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var11));
                        if (var12 != null) {
                            List var13 = this.fieldService.queryFormFields(var12.getId(), false);
                            String var14 = "";
                            String var15 = null;
                            Iterator var16 = var13.iterator();

                            while (var16.hasNext()) {
                                OnlCgformField var17 = (OnlCgformField) var16.next();
                                if (!oConvertUtils.isEmpty(var17.getMainField())) {
                                    var14 = var17.getDbFieldName();
                                    String var18 = var17.getMainField();
                                    var15 = bConstant.getId(var5, var18);
                                }
                            }

                            List var19 = this.fieldService.querySubFormData(var13, var11, var14, var15);
                            if (var19 != null && var19.size() != 0) {
                                var5.put(var11, bConstant.d(var19));
                            } else {
                                var5.put(var11, new String[0]);
                            }
                        }
                    }
                }
            }

            return var5;
        } else {
            throw new AException("找不到字段，请确认配置是否正确!");
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public String editManyFormData(String code, JSONObject json) throws AException, BusinessException {
        OnlCgformHead var3 = this.getTable(code);
        String var4 = "edit";
        this.executeEnhanceJava(var4, "start", var3, json);
        String var5 = var3.getTableName();
        if ("Y".equals(var3.getIsTree())) {
            this.fieldService.editTreeFormData(var3.getId(), var5, json, var3.getTreeIdField(), var3.getTreeParentIdField());
        } else {
            this.fieldService.editFormData(var3.getId(), var5, json, false);
        }

        if (var3.getTableType() == 2) {
            String var6 = var3.getSubTableStr();
            if (oConvertUtils.isNotEmpty(var6)) {
                String[] var7 = var6.split(",");
                String[] var8 = var7;
                int var9 = var7.length;

                for (int var10 = 0; var10 < var9; ++var10) {
                    String var11 = var8[var10];
                    OnlCgformHead var12 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var11));
                    if (var12 != null) {
                        List var13 = this.fieldService.list(Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var12.getId()));
                        String var14 = "";
                        String var15 = null;
                        Iterator var16 = var13.iterator();

                        while (var16.hasNext()) {
                            OnlCgformField var17 = (OnlCgformField) var16.next();
                            if (!oConvertUtils.isEmpty(var17.getMainField())) {
                                var14 = var17.getDbFieldName();
                                String var18 = var17.getMainField();
                                if (json.get(var18.toLowerCase()) != null) {
                                    var15 = json.getString(var18.toLowerCase());
                                }

                                if (json.get(var18.toUpperCase()) != null) {
                                    var15 = json.getString(var18.toUpperCase());
                                }
                            }
                        }

                        if (!oConvertUtils.isEmpty(var15)) {
                            this.fieldService.deleteAutoList(var11, var14, var15);
                            JSONArray var19 = json.getJSONArray(var11);
                            if (var19 != null && var19.size() != 0) {
                                for (int var20 = 0; var20 < var19.size(); ++var20) {
                                    JSONObject var21 = var19.getJSONObject(var20);
                                    if (var15 != null) {
                                        var21.put(var14, var15);
                                    }

                                    this.fieldService.saveFormData(var13, var11, var21);
                                }
                            }
                        }
                    }
                }
            }
        }

        this.executeEnhanceJava(var4, "end", var3, json);
        this.executeEnhanceSql(var4, var3.getId(), json);
        return var5;
    }

    private OnlCgformEnhanceJava a(String var1, String var2, String var3) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> var4 = Wrappers.lambdaQuery(OnlCgformEnhanceJava.class)
                .eq(OnlCgformEnhanceJava::getActiveStatus, "1")
                .eq(OnlCgformEnhanceJava::getButtonCode, var1)
                .eq(OnlCgformEnhanceJava::getEvent, var2)
                .eq(OnlCgformEnhanceJava::getCgformHeadId, var3);
        return this.onlCgformEnhanceJavaMapper.selectOne(var4);
    }

    private Object b(String var1, String var2, String var3) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> var4 = Wrappers.lambdaQuery(OnlCgformEnhanceJava.class)
                .eq(OnlCgformEnhanceJava::getActiveStatus, "1")
                .eq(OnlCgformEnhanceJava::getButtonCode, var1)
                .eq(OnlCgformEnhanceJava::getEvent, var2)
                .eq(OnlCgformEnhanceJava::getCgformHeadId, var3);
        OnlCgformEnhanceJava var5 = this.onlCgformEnhanceJavaMapper.selectOne(var4);
        Object var6 = this.a(var5);
        return var6;
    }

    private void a(JSONObject var1, Object var2, String var3, OnlCgformEnhanceJava var4) throws BusinessException {
        if (var2 != null && var2 instanceof CgformEnhanceJavaInter) {
            CgformEnhanceJavaInter var5 = (CgformEnhanceJavaInter) var2;
            var5.execute(var3, var1);
        } else if (var2 != null && var2 instanceof cgformEnhanceJavaHttpImpl) {
            ((cgformEnhanceJavaHttpImpl) var2).execute(var3, var1, var4);
        }

    }

    public void executeEnhanceJava(String buttonCode, String eventType, OnlCgformHead head, JSONObject json) throws BusinessException {
        OnlCgformEnhanceJava var5 = this.a(buttonCode, eventType, head.getId());
        Object var6 = this.a(var5);
        this.a(json, var6, head.getTableName(), var5);
    }

    public void executeEnhanceExport(OnlCgformHead head, List<Map<String, Object>> dataList) throws BusinessException {
        this.executeEnhanceList(head, "export", dataList);
    }

    public EnhanceDataEnum executeEnhanceImport(OnlCgformHead head, JSONObject json) throws BusinessException {
        OnlCgformEnhanceJava var3 = this.a("import", "start", head.getId());
        Object var4 = this.a(var3);
        if (var4 != null && var4 instanceof CgformEnhanceJavaImportInter) {
            CgformEnhanceJavaImportInter var5 = (CgformEnhanceJavaImportInter) var4;
            return var5.execute(head.getTableName(), json);
        } else {
            return EnhanceDataEnum.INSERT;
        }
    }

    public void executeEnhanceList(OnlCgformHead head, String buttonCode, List<Map<String, Object>> dataList) throws BusinessException {
        LambdaQueryWrapper<OnlCgformEnhanceJava> var4 = Wrappers.lambdaQuery(OnlCgformEnhanceJava.class)
                .eq(OnlCgformEnhanceJava::getActiveStatus, "1")
                .eq(OnlCgformEnhanceJava::getButtonCode, buttonCode)
                .eq(OnlCgformEnhanceJava::getCgformHeadId, head.getId());
        List var5 = this.onlCgformEnhanceJavaMapper.selectList(var4);
        if (var5 != null && !var5.isEmpty()) {
            Object var6 = this.a((OnlCgformEnhanceJava) var5.get(0));
            if (var6 != null && var6 instanceof CgformEnhanceJavaListInter) {
                CgformEnhanceJavaListInter var7 = (CgformEnhanceJavaListInter) var6;
                var7.execute(head.getTableName(), dataList);
            } else if (var6 != null && var6 instanceof cgformEnhanceJavaListHttpImpl) {
                ((cgformEnhanceJavaListHttpImpl) var6).execute(head.getTableName(), dataList, (OnlCgformEnhanceJava) var5.get(0));
            }
        }

    }

    private Object a(OnlCgformEnhanceJava var1) {
        if (var1 != null) {
            String var2 = var1.getCgJavaType();
            String var3 = var1.getCgJavaValue();
            if (oConvertUtils.isNotEmpty(var3)) {
                Object var4 = null;
                if ("class".equals(var2)) {
                    try {
                        var4 = MyClassLoader.getClassByScn(var3).newInstance();
                    } catch (InstantiationException var6) {
                        a.error(var6.getMessage(), var6);
                    } catch (IllegalAccessException var7) {
                        a.error(var7.getMessage(), var7);
                    }
                } else if ("spring".equals(var2)) {
                    var4 = SpringContextUtils.getBean(var3);
                } else if ("http".equals(var2)) {
                    var4 = this.b(var1);
                }

                return var4;
            }
        }

        return null;
    }

    private Object b(OnlCgformEnhanceJava var1) {
        switch (var1.getButtonCode()) {
            case "add":
            case "edit":
            case "delete":
            case "import":
                return this.cgformEnhanceJavaHttp;
            case "export":
            case "query":
                return this.cgformEnhanceJavaListHttp;
            default:
                return this.cgformEnhanceJavaHttp;
        }
    }

    private OnlCgformEnhanceSql c(String var1, String var2) {
        LambdaQueryWrapper<OnlCgformEnhanceSql> var3 = Wrappers.lambdaQuery(OnlCgformEnhanceSql.class)
                .eq(OnlCgformEnhanceSql::getButtonCode, var1)
                .eq(OnlCgformEnhanceSql::getCgformHeadId, var2);
        OnlCgformEnhanceSql var4 = this.onlCgformEnhanceSqlMapper.selectOne(var3);
        return var4;
    }

    private void a(JSONObject var1, OnlCgformEnhanceSql var2) {
        if (var2 != null && oConvertUtils.isNotEmpty(var2.getCgbSql())) {
            String var3 = bConstant.getId(var2.getCgbSql(), var1);
            String[] var4 = var3.split(";");
            String[] var5 = var4;
            int var6 = var4.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];
                if (var8 != null && !"".equals(var8.toLowerCase().trim())) {
                    this.baseMapper.executeDDL(var8);
                }
            }
        }

    }

    public void executeEnhanceSql(String buttonCode, String formId, JSONObject json) {
        OnlCgformEnhanceSql var4 = this.c(buttonCode, formId);
        this.a(json, var4);
    }

    public void executeCustomerButton(String buttonCode, String formId, String dataId) throws BusinessException {
        OnlCgformHead var4 = (OnlCgformHead) this.getById(formId);
        if (var4 == null) {
            throw new BusinessException("未找到表配置信息");
        } else {
            OnlCgformEnhanceJava var5 = this.a(buttonCode, "start", formId);
            OnlCgformEnhanceJava var6 = this.a(buttonCode, "end", formId);
            Object var7 = this.a(var5);
            Object var8 = this.a(var6);
            OnlCgformEnhanceSql var9 = this.c(buttonCode, formId);
            String var10 = var4.getTableName();
            String[] var11 = dataId.split(",");
            LambdaQueryWrapper<OnlCgformField> var12 = Wrappers.lambdaQuery(OnlCgformField.class)
                    .eq(OnlCgformField::getCgformHeadId, formId);
            List var13 = this.onlCgformFieldService.list(var12);
            String[] var14 = var11;
            int var15 = var11.length;

            for (int var16 = 0; var16 < var15; ++var16) {
                String var17 = var14[var16];
                Map var18 = this.baseMapper.queryOneByTableNameAndId(bConstant.f(var4.getTableName()), bConstant.k(var17));
                var18 = this.a(var13, var18);
                JSONObject var19 = JSONObject.parseObject(JSON.toJSONString(var18));
                this.a(var19, var7, var10, var5);
                this.a(var19, var9);
                this.a(var19, var8, var10, var6);
            }

        }
    }

    private Map<String, Object> a(List<OnlCgformField> var1, Map<String, Object> var2) {
        HashMap var3 = new HashMap(5);
        Iterator var4 = var1.iterator();

        while (var4.hasNext()) {
            OnlCgformField var5 = (OnlCgformField) var4.next();
            String var6 = var5.getDbType();
            if (!"blob".equalsIgnoreCase(var6) && !"text".equalsIgnoreCase(var6)) {
                String var7 = var5.getDbFieldName();
                Object var8 = bConstant.b(var2, var7);
                var3.put(var7, var8);
            }
        }

        return var3;
    }

    public List<OnlCgformButton> queryValidButtonList(String headId) {
        LambdaQueryWrapper<OnlCgformButton> var2 = Wrappers.lambdaQuery(OnlCgformButton.class)
                .eq(OnlCgformButton::getCgformHeadId, headId)
                .eq(OnlCgformButton::getButtonStatus, "1")
                .orderByAsc(OnlCgformButton::getOrderNum);
        return this.onlCgformButtonMapper.selectList(var2);
    }

    public OnlCgformEnhanceJs queryEnhanceJs(String formId, String cgJsType) {
        LambdaQueryWrapper<OnlCgformEnhanceJs> var3 = Wrappers.lambdaQuery(OnlCgformEnhanceJs.class)
                .eq(OnlCgformEnhanceJs::getCgformHeadId, formId)
                .eq(OnlCgformEnhanceJs::getCgJsType, cgJsType);
        return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne(var3);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void deleteOneTableInfo(String formId, String dataId) throws BusinessException {
        OnlCgformHead var3 = (OnlCgformHead) this.getById(formId);
        if (var3 == null) {
            throw new BusinessException("未找到表配置信息");
        } else {
            String var4 = bConstant.f(var3.getTableName());
            Map var5 = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(var4, dataId);
            if (var5 != null) {
                Map var6 = bConstant.getId(var5);
                String var7 = "delete";
                JSONObject var8 = JSONObject.parseObject(JSON.toJSONString(var6));
                this.executeEnhanceJava(var7, "start", var3, var8);
                this.updateParentNode(var3, dataId);
                if (var3.getTableType() == 2) {
                    this.fieldService.deleteAutoListMainAndSub(var3, dataId);
                } else {
                    String var9 = "delete from " + var4 + " where id = '" + dataId + "'";
                    ((OnlCgformHeadMapper) this.baseMapper).deleteOne(var9);
                }

                this.executeEnhanceSql(var7, formId, var8);
                this.executeEnhanceJava(var7, "end", var3, var8);
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public JSONObject queryFormItem(OnlCgformHead head, String username) {
        List var3 = this.fieldService.queryAvailableFields(head.getId(), head.getTableName(), head.getTaskId(), false);
        ArrayList var4 = new ArrayList<>();
        List var5;
        if (oConvertUtils.isEmpty(head.getTaskId())) {
            var5 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
            if (var5 != null && var5.size() > 0 && var5.get(0) != null) {
                var4.addAll(var5);
            }
        } else {
            var5 = this.fieldService.queryDisabledFields(head.getTableName(), head.getTaskId());
            if (var5 != null && var5.size() > 0 && var5.get(0) != null) {
                var4.addAll(var5);
            }
        }

        JSONObject var15 = bConstant.getId(var3, var4, null);
        if (head.getTableType() == 2) {
            String var6 = head.getSubTableStr();
            if (oConvertUtils.isNotEmpty(var6)) {
                String[] var7 = var6.split(",");
                int var8 = var7.length;

                for (int var9 = 0; var9 < var8; ++var9) {
                    String var10 = var7[var9];
                    OnlCgformHead var11 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class)
                            .eq(OnlCgformHead::getTableName, var10));
                    if (var11 != null) {
                        List<OnlCgformField> var12 = this.fieldService.queryAvailableFields(var11.getId(), var11.getTableName(), head.getTaskId(), false);
                        new ArrayList<>();
                        List<String> var13;
                        if (oConvertUtils.isNotEmpty(head.getTaskId())) {
                            var13 = this.fieldService.queryDisabledFields(var11.getTableName(), head.getTaskId());
                        } else {
                            var13 = this.onlAuthPageService.queryFormDisabledCode(var11.getId());
                        }

                        JSONObject var14 = new JSONObject();
                        if (1 == var11.getRelationType()) {
                            var14 = bConstant.getId(var12, var13, (i) null);
                        } else {
                            var14.put("columns", bConstant.getId(var12, var13));
                        }

                        var14.put("relationType", var11.getRelationType());
                        var14.put("view", "tab");
                        var14.put("order", var11.getTabOrderNum());
                        var14.put("formTemplate", var11.getFormTemplate());
                        var14.put("describe", var11.getTableTxt());
                        var14.put("key", var11.getTableName());
                        var15.getJSONObject("properties").put(var11.getTableName(), var14);
                    }
                }
            }
        }

        return var15;
    }

    public List<String> generateCode(org.jeecg.modules.online.cgform.model.d model) throws Exception {
        TableVo var2 = new TableVo();
        var2.setEntityName(model.getEntityName());
        var2.setEntityPackage(model.getEntityPackage());
        var2.setFtlDescription(model.getFtlDescription());
        var2.setTableName(model.getTableName());
        var2.setSearchFieldNum(-1);
        ArrayList var3 = new ArrayList<>();
        ArrayList var4 = new ArrayList<>();
        this.a(model.getCode(), var3, var4);
        OnlCgformHead var5 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class)
                .eq(OnlCgformHead::getId, model.getCode()));
        HashMap var6 = new HashMap(5);
        var6.put("scroll", var5.getScroll() == null ? "0" : var5.getScroll().toString());
        String var7 = var5.getFormTemplate();
        if (oConvertUtils.isEmpty(var7)) {
            var2.setFieldRowNum(1);
        } else {
            var2.setFieldRowNum(Integer.parseInt(var7));
        }

        if ("Y".equals(var5.getIsTree())) {
            var6.put("pidField", var5.getTreeParentIdField());
            var6.put("hasChildren", var5.getTreeIdField());
            var6.put("textField", var5.getTreeFieldname());
        }

        if (oConvertUtils.isNotEmpty(model.getVueStyle())) {
            var6.put("vueStyle", model.getVueStyle());
        }

        var2.setExtendParams(var6);
        CgformEnum var8 = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        Object var9 = (new CodeGenerateOne(var2, var3, var4)).generateCodeFile(model.getProjectPath(), var8.getTemplatePath(), var8.getStylePath());
        if (var9 == null || ((List) var9).size() == 0) {
            var9 = new ArrayList<>();
            ((List) var9).add(" :::::: 生成失败ERROR提示 :::::: ");
            ((List) var9).add("1.JeecgBoot项目所在路径是否含有中文或空格，去掉就好了！参考 http://doc.jeecg.com/2088984");
            ((List) var9).add("2.采用JAR包上线发布，需要做额外配置！参考 http://doc.jeecg.com/2043922");
        }

        return (List) var9;
    }

    public List<String> generateOneToMany(org.jeecg.modules.online.cgform.model.d model) throws Exception {
        MainTableVo var2 = new MainTableVo();
        var2.setEntityName(model.getEntityName());
        var2.setEntityPackage(model.getEntityPackage());
        var2.setFtlDescription(model.getFtlDescription());
        var2.setTableName(model.getTableName());
        OnlCgformHead var3 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getId, model.getCode()));
        String var4 = var3.getFormTemplate();
        if (oConvertUtils.isEmpty(var4)) {
            var2.setFieldRowNum(1);
        } else {
            var2.setFieldRowNum(Integer.parseInt(var4));
        }

        ArrayList var5 = new ArrayList<>();
        ArrayList var6 = new ArrayList<>();
        this.a(model.getCode(), (List) var5, (List) var6);
        List var7 = model.getSubList();
        ArrayList var8 = new ArrayList<>();
        Iterator var9 = var7.iterator();

        while (var9.hasNext()) {
            org.jeecg.modules.online.cgform.model.d var10 = (org.jeecg.modules.online.cgform.model.d) var9.next();
            OnlCgformHead var11 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var10.getTableName()));
            if (var11 != null) {
                SubTableVo var12 = new SubTableVo();
                var12.setEntityName(var10.getEntityName());
                var12.setEntityPackage(model.getEntityPackage());
                var12.setTableName(var10.getTableName());
                var12.setFtlDescription(var10.getFtlDescription());
                Integer var13 = var11.getRelationType();
                var12.setForeignRelationType(var13 == 1 ? "1" : "0");
                ArrayList var14 = new ArrayList<>();
                ArrayList var15 = new ArrayList<>();
                OnlCgformField var16 = this.a(var11.getId(), (List) var14, (List) var15);
                if (var16 != null) {
                    var12.setOriginalForeignKeys(new String[]{var16.getDbFieldName()});
                    var12.setForeignKeys(new String[]{var16.getDbFieldName()});
                    var12.setForeignMainKeys(new String[]{var16.getMainField()});
                    var12.setColums(var14);
                    var12.setOriginalColumns(var15);
                    var8.add(var12);
                }
            }
        }

        CgformEnum var17 = CgformEnum.getCgformEnumByConfig(model.getJspMode());
        List var18;
        if (oConvertUtils.isNotEmpty(model.getVueStyle())) {
            var18 = Arrays.asList(var17.getVueStyle());
            HashMap var19 = new HashMap(5);
            if (var18.contains(model.getVueStyle())) {
                var19.put("vueStyle", model.getVueStyle());
            } else {
                a.warn("你选择的页面代码类型：【" + model.getVueStyle() + "】不支持，采用默认类型:" + (String) var18.get(0) + "生成代码！");
                var19.put("vueStyle", var18.get(0));
            }

            var2.setExtendParams(var19);
        }

        if (var8 != null && var8.size() != 0) {
            var18 = (new CodeGenerateOneToMany(var2, var5, var6, var8)).generateCodeFile(model.getProjectPath(), var17.getTemplatePath(), var17.getStylePath());
            return var18;
        } else {
            a.error("你选择的表类型是【主表】，但是没有关联子表，导致生成代码报错！");
            throw new JeecgBootException("你选择的表类型是【主表】，但是没有关联子表，生成代码失败！");
        }
    }

    private OnlCgformField a(String var1, List<ColumnVo> var2, List<ColumnVo> var3) {
        LambdaQueryWrapper var4 = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, var1)
                .eq(OnlCgformField::getDbIsPersist, bLinkConstant.b)
                .orderByAsc(OnlCgformField::getOrderNum);
        List var5 = this.fieldService.list(var4);
        OnlCgformField var6 = null;
        Iterator var7 = var5.iterator();

        while (true) {
            OnlCgformField var8;
            ColumnVo var9;
            do {
                if (!var7.hasNext()) {
                    return var6;
                }

                var8 = (OnlCgformField) var7.next();
                if (oConvertUtils.isNotEmpty(var8.getMainTable())) {
                    var6 = var8;
                }

                var9 = new ColumnVo();
                var9.setFieldLength(var8.getFieldLength());
                var9.setFieldHref(var8.getFieldHref());
                var9.setFieldValidType(var8.getFieldValidType());
                var9.setFieldDefault(var8.getDbDefaultVal());
                var9.setFieldShowType(var8.getFieldShowType());
                var9.setFieldOrderNum(var8.getOrderNum());
                var9.setIsKey(var8.getDbIsKey() == 1 ? "Y" : "N");
                var9.setIsShow(var8.getIsShowForm() == 1 ? "Y" : "N");
                var9.setIsShowList(var8.getIsShowList() == 1 ? "Y" : "N");
                var9.setIsQuery(var8.getIsQuery() == 1 ? "Y" : "N");
                var9.setQueryMode(var8.getQueryMode());
                var9.setDictField(var8.getDictField());
                var9.setDictTable(var8.getDictTable());
                var9.setDictText(var8.getDictText());
                var9.setFieldDbName(var8.getDbFieldName());
                var9.setFieldName(oConvertUtils.camelName(var8.getDbFieldName()));
                var9.setFiledComment(var8.getDbFieldTxt());
                var9.setFieldDbType(var8.getDbType());
                var9.setFieldType(this.b(var8.getDbType()));
                var9.setClassType(var8.getFieldShowType());
                var9.setClassType_row(var8.getFieldShowType());
                if (var8.getDbIsNull() != 0 && !"*".equals(var8.getFieldValidType()) && !"1".equals(var8.getFieldMustInput())) {
                    var9.setNullable("Y");
                } else {
                    var9.setNullable("N");
                }

                if ("switch".equals(var8.getFieldShowType())) {
                    if (oConvertUtils.isNotEmpty(var8.getFieldExtendJson())) {
                        var9.setDictField(var8.getFieldExtendJson());
                    } else {
                        var9.setDictField("is_open");
                    }
                }

                HashMap var10 = new HashMap(5);
                JSONObject var11;
                if (StringUtils.isNotBlank(var8.getFieldExtendJson())) {
                    try {
                        var11 = JSONObject.parseObject(var8.getFieldExtendJson());
                        if (var11 != null) {
                            var10.putAll(var11.getInnerMap());
                        }
                    } catch (JSONException var13) {
                    }
                }

                var9.setExtendParams(var10);
                if ("popup".equals(var8.getFieldShowType())) {
                    boolean var14 = true;
                    Object var12 = var10.get("popupMulti");
                    if (var12 != null) {
                        var14 = (Boolean) var12;
                    }

                    var10.put("popupMulti", var14);
                }

                var9.setSort("1".equals(var8.getSortFlag()) ? "Y" : "N");
                var9.setReadonly(Integer.valueOf(1).equals(var8.getIsReadOnly()) ? "Y" : "N");
                if (oConvertUtils.isNotEmpty(var8.getFieldDefaultValue()) && !var8.getFieldDefaultValue().trim().startsWith("${") && !var8.getFieldDefaultValue().trim().startsWith("#{") && !var8.getFieldDefaultValue().trim().startsWith("{{")) {
                    var9.setDefaultVal(var8.getFieldDefaultValue());
                }

                if (("file".equals(var8.getFieldShowType()) || "image".equals(var8.getFieldShowType())) && oConvertUtils.isNotEmpty(var8.getFieldExtendJson())) {
                    var11 = JSONObject.parseObject(var8.getFieldExtendJson());
                    if (oConvertUtils.isNotEmpty(var11.getString("uploadnum"))) {
                        var9.setUploadnum(var11.getString("uploadnum"));
                    }
                }

                var3.add(var9);
            } while (var8.getIsShowForm() != 1 && var8.getIsShowList() != 1 && var8.getIsQuery() != 1);

            var2.add(var9);
        }
    }

    private String b(String var1) {
        var1 = var1.toLowerCase();
        if (var1.indexOf("int") >= 0) {
            return "java.lang.Integer";
        } else if (var1.indexOf("double") >= 0) {
            return "java.lang.Double";
        } else if (var1.indexOf("decimal") >= 0) {
            return "java.math.BigDecimal";
        } else {
            return var1.indexOf("date") >= 0 ? "java.util.Date" : "java.lang.String";
        }
    }

    public void addCrazyFormData(String tbname, JSONObject json) throws AException, UnsupportedEncodingException {
        OnlCgformHead var3 = this.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, tbname));
        if (var3 == null) {
            throw new AException("数据库主表[" + tbname + "]不存在");
        } else {
            if (var3.getTableType() == 2) {
                String var4 = var3.getSubTableStr();
                if (var4 != null) {
                    String[] var5 = var4.split(",");
                    String[] var6 = var5;
                    int var7 = var5.length;

                    for (int var8 = 0; var8 < var7; ++var8) {
                        String var9 = var6[var8];
                        String var10 = json.getString("sub-table-design_" + var9);
                        if (oConvertUtils.isEmpty(var10)) {
                            var10 = json.getString("sub-table-one2one_" + var9);
                            if (oConvertUtils.isEmpty(var10)) {
                                continue;
                            }
                        }

                        JSONArray var11 = JSONArray.parseArray(var10);
                        if (var11 != null && var11.size() != 0) {
                            OnlCgformHead var12 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var9));
                            if (var12 != null) {
                                List var13 = this.fieldService.list(Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var12.getId()));
                                String var14 = "";
                                String var15 = null;
                                Iterator var16 = var13.iterator();

                                while (var16.hasNext()) {
                                    OnlCgformField var17 = (OnlCgformField) var16.next();
                                    if (!oConvertUtils.isEmpty(var17.getMainField())) {
                                        var14 = var17.getDbFieldName();
                                        String var18 = var17.getMainField();
                                        var15 = json.getString(var18);
                                    }
                                }

                                for (int var19 = 0; var19 < var11.size(); ++var19) {
                                    JSONObject var20 = var11.getJSONObject(var19);
                                    if (var15 != null) {
                                        var20.put(var14, var15);
                                    }

                                    this.fieldService.executeInsertSQL(bConstant.c(var9, var13, var20));
                                }
                            }
                        }
                    }
                }
            }

            this.fieldService.saveFormData(var3.getId(), tbname, json, true);
        }
    }

    public void editCrazyFormData(String tbname, JSONObject json) throws AException, UnsupportedEncodingException {
        OnlCgformHead var3 = this.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, tbname));
        if (var3 == null) {
            throw new AException("数据库主表[" + tbname + "]不存在");
        } else {
            if (var3.getTableType() == 2) {
                String var4 = var3.getSubTableStr();
                if (oConvertUtils.isNotEmpty(var4)) {
                    String[] var5 = var4.split(",");
                    String[] var6 = var5;
                    int var7 = var5.length;

                    for (int var8 = 0; var8 < var7; ++var8) {
                        String var9 = var6[var8];
                        OnlCgformHead var10 = this.baseMapper.selectOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, var9));
                        if (var10 != null) {
                            List var11 = this.fieldService.list(Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var10.getId()));
                            String var12 = "";
                            String var13 = null;
                            Iterator var14 = var11.iterator();

                            while (var14.hasNext()) {
                                OnlCgformField var15 = (OnlCgformField) var14.next();
                                if (!oConvertUtils.isEmpty(var15.getMainField())) {
                                    var12 = var15.getDbFieldName();
                                    String var16 = var15.getMainField();
                                    var13 = json.getString(var16);
                                }
                            }

                            if (!oConvertUtils.isEmpty(var13)) {
                                this.fieldService.deleteAutoList(var9, var12, var13);
                                String var18 = json.getString("sub-table-design_" + var9);
                                if (oConvertUtils.isEmpty(var18)) {
                                    var18 = json.getString("sub-table-one2one_" + var9);
                                    if (oConvertUtils.isEmpty(var18)) {
                                        continue;
                                    }
                                }

                                if (!oConvertUtils.isEmpty(var18)) {
                                    JSONArray var19 = JSONArray.parseArray(var18);
                                    if (var19 != null && var19.size() != 0) {
                                        for (int var20 = 0; var20 < var19.size(); ++var20) {
                                            JSONObject var17 = var19.getJSONObject(var20);
                                            if (var13 != null) {
                                                var17.put(var12, var13);
                                            }

                                            this.fieldService.executeInsertSQL(bConstant.c(var9, var11, var17));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.fieldService.editFormData(var3.getId(), tbname, json, true);
        }
    }

    public Integer getMaxCopyVersion(String physicId) {
        Integer var2 = ((OnlCgformHeadMapper) this.baseMapper).getMaxCopyVersion(physicId);
        return var2 == null ? 0 : var2;
    }

    public void copyOnlineTableConfig(OnlCgformHead physicTable) throws Exception {
        String var2 = physicTable.getId();
        OnlCgformHead var3 = new OnlCgformHead();
        String var4 = UUIDGenerator.generate();
        var3.setId(var4);
        var3.setPhysicId(var2);
        var3.setCopyType(1);
        var3.setCopyVersion(physicTable.getTableVersion());
        var3.setTableVersion(1);
        var3.setTableName(this.d(var2, physicTable.getTableName()));
        var3.setTableTxt(physicTable.getTableTxt());
        var3.setFormCategory(physicTable.getFormCategory());
        var3.setFormTemplate(physicTable.getFormTemplate());
        var3.setFormTemplateMobile(physicTable.getFormTemplateMobile());
        var3.setIdSequence(physicTable.getIdSequence());
        var3.setIdType(physicTable.getIdType());
        var3.setIsCheckbox(physicTable.getIsCheckbox());
        var3.setIsPage(physicTable.getIsPage());
        var3.setIsTree(physicTable.getIsTree());
        var3.setQueryMode(physicTable.getQueryMode());
        var3.setTableType(1);
        var3.setIsDbSynch("N");
        var3.setIsDesForm(physicTable.getIsDesForm());
        var3.setDesFormCode(physicTable.getDesFormCode());
        var3.setTreeParentIdField(physicTable.getTreeParentIdField());
        var3.setTreeFieldname(physicTable.getTreeFieldname());
        var3.setTreeIdField(physicTable.getTreeIdField());
        var3.setRelationType(null);
        var3.setTabOrderNum(null);
        var3.setSubTableStr(null);
        var3.setThemeTemplate(physicTable.getThemeTemplate());
        var3.setScroll(physicTable.getScroll());
        var3.setExtConfigJson(physicTable.getExtConfigJson());
        List var5 = this.fieldService.list(Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var2));
        Iterator var6 = var5.iterator();

        while (var6.hasNext()) {
            OnlCgformField var7 = (OnlCgformField) var6.next();
            OnlCgformField var8 = new OnlCgformField();
            var8.setCgformHeadId(var4);
            this.a(var7, var8);
            this.fieldService.save(var8);
        }

        ((OnlCgformHeadMapper) this.baseMapper).insert(var3);
    }

    public void initCopyState(List<OnlCgformHead> headList) {
        List var2 = ((OnlCgformHeadMapper) this.baseMapper).queryCopyPhysicId();
        Iterator var3 = headList.iterator();

        while (var3.hasNext()) {
            OnlCgformHead var4 = (OnlCgformHead) var3.next();
            if (var2.contains(var4.getId())) {
                var4.setHascopy(1);
            } else {
                var4.setHascopy(0);
            }
        }

    }

    public void deleteBatch(String ids, String flag) {
        String[] var3 = ids.split(",");
        if ("1".equals(flag)) {
            String[] var4 = var3;
            int var5 = var3.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String var7 = var4[var6];

                try {
                    this.deleteRecordAndTable(var7);
                } catch (AException var9) {
                    var9.printStackTrace();
                } catch (SQLException var10) {
                    var10.printStackTrace();
                }
            }
        } else {
            this.removeByIds(Arrays.asList(var3));
        }

    }

    public void updateParentNode(OnlCgformHead head, String dataId) {
        if ("Y".equals(head.getIsTree())) {
            String var3 = bConstant.f(head.getTableName());
            String var4 = head.getTreeParentIdField();
            Map var5 = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(var3, dataId);
            String var6 = null;
            if (var5.get(var4) != null && !"0".equals(var5.get(var4))) {
                var6 = var5.get(var4).toString();
            } else if (var5.get(var4.toUpperCase()) != null && !"0".equals(var5.get(var4.toUpperCase()))) {
                var6 = var5.get(var4.toUpperCase()).toString();
            }

            if (var6 != null) {
                Integer var7 = ((OnlCgformHeadMapper) this.baseMapper).queryChildNode(var3, var4, var6);
                if (var7 == 1) {
                    String var8 = head.getTreeIdField();
                    this.fieldService.updateTreeNodeNoChild(var3, var8, var6);
                }
            }
        }

    }

    private void b(OnlCgformHead var1, List<OnlCgformField> var2) {
        List var3 = this.list(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getPhysicId, var1.getId()));
        if (var3 != null && var3.size() > 0) {
            Iterator var4 = var3.iterator();

            while (true) {
                List var6;
                String var13;
                ArrayList var19;
                Iterator var22;
                label108:
                do {
                    while (var4.hasNext()) {
                        OnlCgformHead var5 = (OnlCgformHead) var4.next();
                        var6 = this.fieldService.list(Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, var5.getId()));
                        OnlCgformField var9;
                        if (var6 != null && var6.size() != 0) {
                            HashMap var15 = new HashMap(5);
                            Iterator var16 = var6.iterator();

                            while (var16.hasNext()) {
                                var9 = (OnlCgformField) var16.next();
                                var15.put(var9.getDbFieldName(), 1);
                            }

                            HashMap var17 = new HashMap(5);
                            Iterator var18 = var2.iterator();

                            while (var18.hasNext()) {
                                OnlCgformField var10 = (OnlCgformField) var18.next();
                                var17.put(var10.getDbFieldName(), 1);
                            }

                            var19 = new ArrayList<>();
                            ArrayList var20 = new ArrayList<>();
                            Iterator var11 = var17.keySet().iterator();

                            while (var11.hasNext()) {
                                String var12 = (String) var11.next();
                                if (var15.get(var12) == null) {
                                    var20.add(var12);
                                } else {
                                    var19.add(var12);
                                }
                            }

                            ArrayList var21 = new ArrayList<>();
                            var22 = var15.keySet().iterator();

                            while (var22.hasNext()) {
                                var13 = (String) var22.next();
                                if (var17.get(var13) == null) {
                                    var21.add(var13);
                                }
                            }

                            OnlCgformField var23;
                            if (var21.size() > 0) {
                                var22 = var6.iterator();

                                while (var22.hasNext()) {
                                    var23 = (OnlCgformField) var22.next();
                                    if (var21.contains(var23.getDbFieldName())) {
                                        this.fieldService.removeById(var23.getId());
                                    }
                                }
                            }

                            if (var20.size() > 0) {
                                var22 = var2.iterator();

                                while (var22.hasNext()) {
                                    var23 = (OnlCgformField) var22.next();
                                    if (var20.contains(var23.getDbFieldName())) {
                                        OnlCgformField var14 = new OnlCgformField();
                                        var14.setCgformHeadId(var5.getId());
                                        this.a(var23, var14);
                                        this.fieldService.save(var14);
                                    }
                                }
                            }
                            continue label108;
                        }

                        Iterator var7 = var2.iterator();

                        while (var7.hasNext()) {
                            OnlCgformField var8 = (OnlCgformField) var7.next();
                            var9 = new OnlCgformField();
                            var9.setCgformHeadId(var5.getId());
                            this.a(var8, var9);
                            this.fieldService.save(var9);
                        }
                    }

                    return;
                } while (var19.size() <= 0);

                var22 = var19.iterator();

                while (var22.hasNext()) {
                    var13 = (String) var22.next();
                    this.b(var13, var2, var6);
                }
            }
        }
    }

    private void b(String var1, List<OnlCgformField> var2, List<OnlCgformField> var3) {
        OnlCgformField var4 = null;
        Iterator var5 = var2.iterator();

        while (var5.hasNext()) {
            OnlCgformField var6 = (OnlCgformField) var5.next();
            if (var1.equals(var6.getDbFieldName())) {
                var4 = var6;
            }
        }

        OnlCgformField var8 = null;
        Iterator var9 = var3.iterator();

        while (var9.hasNext()) {
            OnlCgformField var7 = (OnlCgformField) var9.next();
            if (var1.equals(var7.getDbFieldName())) {
                var8 = var7;
            }
        }

        if (var4 != null && var8 != null) {
            boolean var10 = false;
            if (!var4.getDbType().equals(var8.getDbType())) {
                var8.setDbType(var4.getDbType());
                var10 = true;
            }

            if (var4.getDbDefaultVal() != null && !var4.getDbDefaultVal().equals(var8.getDbDefaultVal())) {
                var8.setDbDefaultVal(var4.getDbDefaultVal());
                var10 = true;
            }

            if (!var4.getDbLength().equals(var8.getDbLength())) {
                var8.setDbLength(var4.getDbLength());
                var10 = true;
            }

            if (var4.getDbIsNull() != var8.getDbIsNull()) {
                var8.setDbIsNull(var4.getDbIsNull());
                var10 = true;
            }

            if (var10) {
                this.fieldService.updateById(var8);
            }
        }

    }

    private void a(OnlCgformField var1, OnlCgformField var2) {
        var2.setDbDefaultVal(var1.getDbDefaultVal());
        var2.setDbFieldName(var1.getDbFieldName());
        var2.setDbFieldNameOld(var1.getDbFieldNameOld());
        var2.setDbFieldTxt(var1.getDbFieldTxt());
        var2.setDbIsKey(var1.getDbIsKey());
        var2.setDbIsNull(var1.getDbIsNull());
        var2.setDbLength(var1.getDbLength());
        var2.setDbPointLength(var1.getDbPointLength());
        var2.setDbType(var1.getDbType());
        var2.setDictField(var1.getDictField());
        var2.setDictTable(var1.getDictTable());
        var2.setDictText(var1.getDictText());
        var2.setFieldExtendJson(var1.getFieldExtendJson());
        var2.setFieldHref(var1.getFieldHref());
        var2.setFieldLength(var1.getFieldLength());
        var2.setFieldMustInput(var1.getFieldMustInput());
        var2.setFieldShowType(var1.getFieldShowType());
        var2.setFieldValidType(var1.getFieldValidType());
        var2.setFieldDefaultValue(var1.getFieldDefaultValue());
        var2.setIsQuery(var1.getIsQuery());
        var2.setIsShowForm(var1.getIsShowForm());
        var2.setIsShowList(var1.getIsShowList());
        var2.setMainField(var1.getMainField());
        var2.setMainTable(var1.getMainTable());
        var2.setOrderNum(var1.getOrderNum());
        var2.setQueryMode(var1.getQueryMode());
        var2.setIsReadOnly(var1.getIsReadOnly());
        var2.setSortFlag(var1.getSortFlag());
        var2.setQueryDefVal(var1.getQueryDefVal());
        var2.setQueryConfigFlag(var1.getQueryConfigFlag());
        var2.setQueryDictField(var1.getQueryDictField());
        var2.setQueryDictTable(var1.getQueryDictTable());
        var2.setQueryDictText(var1.getQueryDictText());
        var2.setQueryMustInput(var1.getQueryMustInput());
        var2.setQueryShowType(var1.getQueryShowType());
        var2.setQueryValidType(var1.getQueryValidType());
        var2.setConverter(var1.getConverter());
        var2.setDbIsPersist(var1.getDbIsPersist());
    }

    private void a(OnlCgformField var1) {
        if ("Text".equals(var1.getDbType()) || "Blob".equals(var1.getDbType())) {
            var1.setDbLength(0);
            var1.setDbPointLength(0);
        }

    }

    private String d(String var1, String var2) {
        List var3 = ((OnlCgformHeadMapper) this.baseMapper).queryAllCopyTableName(var1);
        int var4 = 0;
        if (var3 != null || var3.size() > 0) {
            for (int var5 = 0; var5 < var3.size(); ++var5) {
                String var6 = (String) var3.get(var5);
                int var7 = Integer.parseInt(var6.split("\\$")[1]);
                if (var7 > var4) {
                    var4 = var7;
                }
            }
        }

        StringBuilder var10000 = (new StringBuilder()).append(var2).append("$");
        ++var4;
        return var10000.append(var4).toString();
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public String deleteDataByCode(String cgformCode, String dataIds) {
        OnlCgformHead var3 = super.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, cgformCode));
        if (var3 == null) {
            throw new JeecgBootException("实体不存在");
        } else {
            String var4 = var3.getTableName();

            try {
                if (dataIds.indexOf(",") > 0) {
                    this.onlCgformFieldService.deleteAutoListById(var4, dataIds);
                } else {
                    this.deleteOneTableInfo(var3.getId(), dataIds);
                }

                return var4;
            } catch (Exception var6) {
                a.error("OnlCgformApiController.formEdit()发生异常：" + var6.getMessage(), var6);
                throw new JeecgBootException("删除失败：" + var6.getMessage());
            }
        }
    }

    public JSONObject queryAllDataByTableNameForDesform(String tableName, String dataIds) throws AException {
        JSONObject var3 = new JSONObject();
        LambdaQueryWrapper var4 = Wrappers.lambdaQuery(OnlCgformHead.class)
                .eq(OnlCgformHead::getTableName, tableName);
        OnlCgformHead var5 = super.getOne(var4);
        if (var5 == null) {
            throw new JeecgBootException("表单数据不存在！");
        } else {
            Map var6 = this.queryManyFormData(var5.getId(), dataIds);
            if (var6 == null) {
                throw new JeecgBootException("表单数据查询失败！");
            } else {
                JSONObject var7 = JSON.parseObject(JSON.toJSONString(var6));
                String var8 = var5.getSubTableStr();
                if (oConvertUtils.isNotEmpty(var8)) {
                    ArrayList var9 = new ArrayList(Arrays.asList(var8.split(",")));
                    LambdaQueryWrapper var10 = Wrappers.lambdaQuery(OnlCgformHead.class)
                            .in(OnlCgformHead::getTableName, var9);
                    List var11 = super.list(var10);
                    JSONObject var12 = new JSONObject();
                    JSONObject var13 = new JSONObject();

                    OnlCgformHead var15;
                    for (Iterator var14 = var11.iterator(); var14.hasNext(); var7.remove(var15.getTableName())) {
                        var15 = (OnlCgformHead) var14.next();
                        JSONArray var16 = var7.getJSONArray(var15.getTableName());
                        if (var16 != null && var16.size() > 0) {
                            if (0 == var15.getRelationType()) {
                                var12.put(var15.getTableName(), var16);
                            } else {
                                JSONObject var17 = var16.getJSONObject(0);
                                var13.put(var15.getTableName(), var17);
                            }
                        }
                    }

                    var3.put("one2one", var13);
                    var3.put("one2many", var12);
                }

                var3.put("main", var7);
                return var3;
            }
        }
    }

    @AutoLowApp(
            action = LowAppAopEnum.COPY,
            bizType = "cgform"
    )
    public OnlCgformHead copyOnlineTable(String id, String tableName) {
        LambdaQueryWrapper var3 = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, tableName);
        Long var4 = this.baseMapper.selectCount(var3);
        if (var4 != null && var4 >= 1L) {
            throw new JeecgBootException("表名已经存在!");
        } else {
            OnlCgformHead var5 = this.baseMapper.selectById(id);
            if (var5 == null) {
                throw new JeecgBootException("表不存在!");
            } else {
                OnlCgformHead var6 = new OnlCgformHead();
                BeanUtils.copyProperties(var5, var6);
                String var7 = bConstant.getId();
                var6.setId(var7);
                var6.setSubTableStr((String) null);
                var6.setTableName(tableName);
                var6.setTableVersion(1);
                var6.setIsDbSynch("N");
                var6.setCreateBy((String) null);
                var6.setCreateTime((Date) null);
                var6.setUpdateBy(null);
                var6.setUpdateTime(null);
                LambdaQueryWrapper<OnlCgformField> var8 = Wrappers.lambdaQuery(OnlCgformField.class)
                        .eq(OnlCgformField::getCgformHeadId, id);
                List var9 = this.fieldService.list(var8);
                ArrayList var10 = new ArrayList<>();
                if (var9 != null && var9.size() > 0) {
                    Iterator var11 = var9.iterator();

                    while (var11.hasNext()) {
                        OnlCgformField var12 = (OnlCgformField) var11.next();
                        OnlCgformField var13 = new OnlCgformField();
                        BeanUtils.copyProperties(var12, var13);
                        var13.setCgformHeadId(var7);
                        var13.setMainField((String) null);
                        var13.setMainTable((String) null);
                        var13.setId((String) null);
                        var13.setCreateBy((String) null);
                        var13.setCreateTime((Date) null);
                        var13.setUpdateBy((String) null);
                        var13.setUpdateTime((Date) null);
                        var10.add(var13);
                    }
                }

                LambdaQueryWrapper var17 = Wrappers.lambdaQuery(OnlCgformIndex.class)
                        .eq(OnlCgformIndex::getCgformHeadId, id);
                List var18 = this.indexService.list(var17);
                ArrayList var19 = new ArrayList<>();
                if (var18 != null && var18.size() > 0) {
                    Iterator var14 = var18.iterator();

                    while (var14.hasNext()) {
                        OnlCgformIndex var15 = (OnlCgformIndex) var14.next();
                        OnlCgformIndex var16 = new OnlCgformIndex();
                        BeanUtils.copyProperties(var15, var16);
                        var16.setCgformHeadId(var7);
                        var16.setId((String) null);
                        var16.setCreateBy((String) null);
                        var16.setCreateTime((Date) null);
                        var16.setUpdateBy((String) null);
                        var16.setUpdateTime((Date) null);
                        var19.add(var16);
                    }
                }

                this.save(var6);
                this.fieldService.saveBatch(var10);
                this.indexService.saveBatch(var19);
                return var6;
            }
        }
    }

    public OnlCgformHead getTable(String code) throws AException {
        OnlCgformHead var2 = this.getById(code);
        if (var2 == null) {
            LambdaQueryWrapper var3 = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, code);
            var2 = this.baseMapper.selectOne(var3);
        }

        if (var2 == null) {
            throw new AException("online表[" + code + "]不存在");
        } else {
            return var2;
        }
    }

    private bDataBaseConfig getOnlineDataBaseConfig() {
        if (oConvertUtils.isEmpty(this.onlineDatasource)) {
            return this.dataBaseConfig;
        } else {
            DataSourceProperty var1 = CommonUtils.getDataSourceProperty(this.onlineDatasource);
            if (var1 == null) {
                a.error("jeecg.online.datasource配置错误,获取不到数据源返回master");
                return this.dataBaseConfig;
            } else {
                bDataBaseConfig var2 = new bDataBaseConfig();
                var2.setDriverClassName(var1.getDriverClassName());
                var2.setPassword(var1.getPassword());
                var2.setUsername(var1.getUsername());
                var2.setUrl(var1.getUrl());
                var2.setDmDataBaseConfig(new cDataBaseConfig());
                return var2;
            }
        }
    }
}
