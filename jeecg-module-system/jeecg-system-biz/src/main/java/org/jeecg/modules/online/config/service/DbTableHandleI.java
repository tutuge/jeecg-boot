package org.jeecg.modules.online.config.service;

import org.jeecg.modules.online.config.dUtil.aUtil;
import org.jeecg.modules.online.config.exception.AException;

import java.util.List;

public interface DbTableHandleI {
    String getAddColumnSql(aUtil parama);

    String getReNameFieldName(aUtil parama);

    String getUpdateColumnSql(aUtil parama1, aUtil parama2) throws AException;

    String getMatchClassTypeByDataType(String paramString, int paramInt);

    String dropTableSQL(String paramString);

    String getDropColumnSql(String paramString);

    String getCommentSql(aUtil parama);

    String getSpecialHandle(aUtil parama1, aUtil parama2);

    String dropIndexs(String paramString1, String paramString2);

    String countIndex(String paramString1, String paramString2);

    default void handleUpdateMultiSql(aUtil meta, aUtil config, String tableName, List<String> sqlList) {
    }
}
