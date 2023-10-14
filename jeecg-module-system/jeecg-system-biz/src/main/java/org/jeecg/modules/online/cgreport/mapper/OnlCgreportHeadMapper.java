package org.jeecg.modules.online.cgreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;

public interface OnlCgreportHeadMapper extends BaseMapper<OnlCgreportHead> {
  List<Map<String, Object>> executeSelect(@Param("sql") String paramString);
  
  List<Map<?, ?>> executeSelete(@Param("sql") String paramString);
  
  List<DictModel> queryDictListBySql(@Param("sql") String paramString);
  
  IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString);
  
  Long queryCountBySql(@Param("sql") String paramString);
  
  Map<String, Object> queryCgReportMainConfig(@Param("reportId") String paramString);
  
  List<Map<String, Object>> queryCgReportItems(@Param("cgrheadId") String paramString);
  
  List<OnlCgreportParam> queryCgReportParams(@Param("cgrheadId") String paramString);
  
  List<Map<String, Object>> selectByCondition(@Param("sqlStr") String paramString, @Param("param") Map<String, Object> paramMap);
  
  IPage<Map<String, Object>> selectPageByCondition(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString, @Param("param") Map<String, Object> paramMap);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\mapper\OnlCgreportHeadMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */