package org.jeecg.modules.online.config.service;

import java.util.List;
import org.jeecg.modules.online.config.dUtil.a;

public interface DbTableHandleI {
  String getAddColumnSql(a parama);

  String getReNameFieldName(a parama);

  String getUpdateColumnSql(a parama1, a parama2) throws a;

  String getMatchClassTypeByDataType(String paramString, int paramInt);

  String dropTableSQL(String paramString);

  String getDropColumnSql(String paramString);

  String getCommentSql(a parama);

  String getSpecialHandle(a parama1, a parama2);

  String dropIndexs(String paramString1, String paramString2);

  String countIndex(String paramString1, String paramString2);

  default void handleUpdateMultiSql(a meta, a config, String tableName, List<String> sqlList) {}
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\service\DbTableHandleI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
