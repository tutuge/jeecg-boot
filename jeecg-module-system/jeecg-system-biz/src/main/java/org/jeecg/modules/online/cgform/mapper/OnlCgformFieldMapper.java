package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.a1.aEntity;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.model.TreeModel;

public interface OnlCgformFieldMapper extends BaseMapper<OnlCgformField> {
  List<Map<String, Object>> queryListBySql(@Param("sqlStr") String paramString);

  Integer queryCountBySql(@Param("sqlStr") String paramString);

  void deleteAutoList(@Param("sqlStr") String paramString);

  void saveFormData(@Param("sqlStr") String paramString);

  void editFormData(@Param("sqlStr") String paramString);

  Map<String, Object> queryFormData(@Param("sqlStr") String paramString);

  List<Map<String, Object>> queryListData(@Param("sqlStr") String paramString);

  IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString);

  void executeInsertSQL(Map<String, Object> paramMap);

  void executeUpdatetSQL(Map<String, Object> paramMap);

  List<String> selectOnlineHideColumns(@Param("user_id") String paramString1, @Param("online_tbname") String paramString2);

  List<String> selectOnlineDisabledColumns(@Param("user_id") String paramString1, @Param("online_tbname") String paramString2);

  List<String> selectFlowAuthColumns(@Param("table_name") String paramString1, @Param("task_id") String paramString2, @Param("rule_type") String paramString3);

  @Deprecated
  List<TreeModel> queryDataListByLinkDown(@Param("query") aEntity parama);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\mapper\OnlCgformFieldMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
