package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

public interface OnlCgformIndexMapper extends BaseMapper<OnlCgformIndex> {
  int queryIndexCount(@Param("sqlStr") String paramString);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\mapper\OnlCgformIndexMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */