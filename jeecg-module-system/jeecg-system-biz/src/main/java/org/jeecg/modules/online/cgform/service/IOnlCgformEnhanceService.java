package org.jeecg.modules.online.cgform.service;

import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;

public interface IOnlCgformEnhanceService {
  List<OnlCgformEnhanceJava> queryEnhanceJavaList(String paramString);
  
  void saveEnhanceJava(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);
  
  void updateEnhanceJava(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);
  
  void deleteEnhanceJava(String paramString);
  
  void deleteBatchEnhanceJava(List<String> paramList);
  
  boolean checkOnlyEnhance(OnlCgformEnhanceJava paramOnlCgformEnhanceJava);
  
  boolean checkOnlyEnhance(OnlCgformEnhanceSql paramOnlCgformEnhanceSql);
  
  List<OnlCgformEnhanceSql> queryEnhanceSqlList(String paramString);
  
  void saveEnhanceSql(OnlCgformEnhanceSql paramOnlCgformEnhanceSql);
  
  void updateEnhanceSql(OnlCgformEnhanceSql paramOnlCgformEnhanceSql);
  
  void deleteEnhanceSql(String paramString);
  
  void deleteBatchEnhanceSql(List<String> paramList);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\IOnlCgformEnhanceService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */