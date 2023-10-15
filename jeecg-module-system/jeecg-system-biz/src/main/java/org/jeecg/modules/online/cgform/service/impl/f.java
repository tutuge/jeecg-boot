package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.converter.b;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.dConstants.hConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformSqlService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("onlCgformSqlServiceImpl")
public class f implements IOnlCgformSqlService {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    public void saveBatchOnlineTable(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) throws BusinessException {
        SqlSession sqlSession = null;
        try {
            b.a(2, dataList, fieldList);
            sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            OnlCgformFieldMapper onlCgformFieldMapper = (OnlCgformFieldMapper) sqlSession.getMapper(OnlCgformFieldMapper.class);
            char c = 'Ϩ';
            if (c >= dataList.size()) {
                for (byte b = 0; b < dataList.size(); b++) {
                    String str = JSON.toJSONString(dataList.get(b));
                    a(str, head, fieldList, onlCgformFieldMapper);
                }
            } else {
                for (byte b = 0; b < dataList.size(); b++) {
                    String str = JSON.toJSONString(dataList.get(b));
                    a(str, head, fieldList, onlCgformFieldMapper);
                    if (b % c == 0) {
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                }
            }
            sqlSession.commit();
        } catch (Exception exception) {
            sqlSession.rollback();
            throw new BusinessException(exception.getMessage());
        } finally {
            sqlSession.close();
        }
    }

    public void saveOrUpdateSubData(String subDataJsonStr, OnlCgformHead head, List<OnlCgformField> subFiledList) throws BusinessException {
        OnlCgformFieldMapper onlCgformFieldMapper = (OnlCgformFieldMapper) SpringContextUtils.getBean(OnlCgformFieldMapper.class);
        a(subDataJsonStr, head, subFiledList, onlCgformFieldMapper);
    }

    public Map<String, String> saveOnlineImportDataWithValidate(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) {
        StringBuffer stringBuffer = new StringBuffer();
        hConstant h = new hConstant(fieldList);
        OnlCgformFieldMapper onlCgformFieldMapper = (OnlCgformFieldMapper) SpringContextUtils.getBean(OnlCgformFieldMapper.class);
        byte b1 = 0, b2 = 0;
        int i = dataList.size();
        for (byte b3 = 0; b3 < i; b3++) {
            String str1 = JSON.toJSONString(dataList.get(b3));
            String str2 = h.a(str1, ++b1);
            if (str2 == null) {
                try {
                    a(str1, head, fieldList, onlCgformFieldMapper);
                } catch (Exception exception) {
                    b2++;
                    String str3 = a(exception.getCause().getMessage());
                    String str4 = h.b(str3, b1);
                    stringBuffer.append(str4);
                }
            } else {
                b2++;
                stringBuffer.append(str2);
            }
        }
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("error", stringBuffer.toString());
        hashMap.put("tip", h.a(i, b2));
        return (Map) hashMap;
    }

    private void a(String paramString, OnlCgformHead paramOnlCgformHead, List<OnlCgformField> paramList, OnlCgformFieldMapper paramOnlCgformFieldMapper) throws BusinessException {
        JSONObject jSONObject = JSONObject.parseObject(paramString);
        EnhanceDataEnum enhanceDataEnum = this.onlCgformHeadService.executeEnhanceImport(paramOnlCgformHead, jSONObject);
        String str = paramOnlCgformHead.getTableName();
        if (EnhanceDataEnum.INSERT == enhanceDataEnum) {
            Map map = bConstant.getId(str, paramList, jSONObject);
            paramOnlCgformFieldMapper.executeInsertSQL(map);
        } else if (EnhanceDataEnum.UPDATE == enhanceDataEnum) {
            Map map = bConstant.b(str, paramList, jSONObject);
            paramOnlCgformFieldMapper.executeUpdatetSQL(map);
        } else if (EnhanceDataEnum.ABANDON == enhanceDataEnum) {

        }
    }

    private String a(String paramString) {
        String str = "^Duplicate entry \\'(.*)\\' for key .*$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(paramString);
        if (matcher.find())
            return "重复数据" + matcher.group(1);
        return paramString;
    }
}
