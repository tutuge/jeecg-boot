package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import freemarker.template.TemplateException;
import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.model.a;
import org.jeecg.modules.online.cgform.model.dModel;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecg.modules.online.config.exception.BusinessException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOnlCgformHeadService extends IService<OnlCgformHead> {
    Result<?> addAll(a parama);

    Result<?> editAll(a parama);

    void doDbSynch(String paramString1, String paramString2) throws HibernateException, IOException,
            TemplateException, SQLException, AException;

    void deleteRecordAndTable(String paramString) throws AException, SQLException;

    void deleteRecord(String paramString) throws AException, SQLException;

    List<Map<String, Object>> queryListData(String paramString);

    OnlCgformEnhanceJs queryEnhance(String paramString1, String paramString2);

    void saveEnhance(OnlCgformEnhanceJs paramOnlCgformEnhanceJs);

    void editEnhance(OnlCgformEnhanceJs paramOnlCgformEnhanceJs);

    OnlCgformEnhanceSql queryEnhanceSql(String paramString1, String paramString2);

    OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);

    List<OnlCgformButton> queryButtonList(String paramString, boolean paramBoolean);

    List<OnlCgformButton> queryButtonList(String paramString);

    List<String> queryOnlinetables();

    void saveDbTable2Online(String paramString);

    JSONObject queryFormItem(OnlCgformHead paramOnlCgformHead, String paramString);

    String saveManyFormData(String paramString1, JSONObject paramJSONObject, String paramString2) throws AException, BusinessException;

    Map<String, Object> queryManyFormData(String paramString1, String paramString2) throws AException;

    List<Map<String, Object>> queryManySubFormData(String paramString1, String paramString2) throws AException;

    Map<String, Object> querySubFormData(String paramString1, String paramString2) throws AException;

    String editManyFormData(String paramString, JSONObject paramJSONObject) throws AException, BusinessException;

    void executeEnhanceJava(String paramString1, String paramString2, OnlCgformHead paramOnlCgformHead, JSONObject paramJSONObject) throws BusinessException;

    void executeEnhanceExport(OnlCgformHead paramOnlCgformHead, List<Map<String, Object>> paramList) throws BusinessException;

    EnhanceDataEnum executeEnhanceImport(OnlCgformHead paramOnlCgformHead, JSONObject paramJSONObject) throws BusinessException;

    void executeEnhanceList(OnlCgformHead paramOnlCgformHead, String paramString, List<Map<String, Object>> paramList) throws BusinessException;

    void executeEnhanceSql(String paramString1, String paramString2, JSONObject paramJSONObject);

    void executeCustomerButton(String paramString1, String paramString2, String paramString3) throws BusinessException;

    List<OnlCgformButton> queryValidButtonList(String paramString);

    OnlCgformEnhanceJs queryEnhanceJs(String paramString1, String paramString2);

    void deleteOneTableInfo(String paramString1, String paramString2) throws BusinessException;

    List<String> generateCode(dModel paramd) throws Exception;

    List<String> generateOneToMany(dModel paramd) throws Exception;

    void addCrazyFormData(String paramString, JSONObject paramJSONObject) throws AException, UnsupportedEncodingException;

    void editCrazyFormData(String paramString, JSONObject paramJSONObject) throws AException, UnsupportedEncodingException;

    Integer getMaxCopyVersion(String paramString);

    void copyOnlineTableConfig(OnlCgformHead paramOnlCgformHead) throws Exception;

    void initCopyState(List<OnlCgformHead> paramList);

    void deleteBatch(String paramString1, String paramString2);

    void updateParentNode(OnlCgformHead paramOnlCgformHead, String paramString);

    String deleteDataByCode(String paramString1, String paramString2);

    JSONObject queryAllDataByTableNameForDesform(String paramString1, String paramString2) throws AException;

    OnlCgformHead copyOnlineTable(String paramString1, String paramString2);

    OnlCgformHead getTable(String paramString) throws AException;
}
