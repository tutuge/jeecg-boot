package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface OnlineMapper {
  List<Map<String, Object>> selectByCondition(@Param("sqlStr") String paramString, @Param("param") Map<String, Object> paramMap);
  
  IPage<Map<String, Object>> selectPageByCondition(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString, @Param("param") Map<String, Object> paramMap);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\mapper\OnlineMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */