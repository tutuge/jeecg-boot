package org.jeecg.modules.online.cgreport.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.config.exception.AException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOnlCgreportHeadService extends IService<OnlCgreportHead> {
    Result<?> editAll(OnlCgreportModel paramOnlCgreportModel);

    Result<?> delete(String paramString);

    Result<?> bathDelete(String[] paramArrayOfString);

    Map<String, Object> executeSelectSql(String paramString1, String paramString2, Map<String, Object> paramMap) throws SQLException;

    Map<String, Object> executeSelectSqlDynamic(String paramString1, String paramString2, Map<String, Object> paramMap, String paramString3);

    List<String> getSqlFields(String paramString1, String paramString2) throws SQLException, AException;

    List<String> getSqlParams(String paramString);

    Map<String, Object> queryCgReportConfig(String paramString);

    List<DictModel> queryDictSelectData(String paramString1, String paramString2);

    Map<String, Object> queryColumnInfo(String paramString, boolean paramBoolean);

    List<DictModel> queryColumnDict(String paramString1, JSONArray paramJSONArray, String paramString2);

    List<DictModel> queryColumnDictList(String paramString1, List<Map<String, Object>> paramList, String paramString2);
}
