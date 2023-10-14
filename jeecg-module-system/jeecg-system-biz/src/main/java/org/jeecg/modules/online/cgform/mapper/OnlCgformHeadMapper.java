package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;

public interface OnlCgformHeadMapper extends BaseMapper<OnlCgformHead> {
  @InterceptorIgnore(tenantLine = "true")
  void executeDDL(@Param("sqlStr") String paramString);
  
  List<Map<String, Object>> queryList(@Param("sqlStr") String paramString);
  
  List<String> queryOnlinetables();
  
  Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String paramString1, @Param("dataId") String paramString2);
  
  void deleteOne(@Param("sqlStr") String paramString);
  
  @Select({"select max(copy_version) from onl_cgform_head where physic_id = #{physicId}"})
  Integer getMaxCopyVersion(@Param("physicId") String paramString);
  
  @Select({"select table_name from onl_cgform_head where physic_id = #{physicId}"})
  List<String> queryAllCopyTableName(@Param("physicId") String paramString);
  
  @Select({"select physic_id from onl_cgform_head GROUP BY physic_id"})
  List<String> queryCopyPhysicId();
  
  String queryCategoryIdByCode(@Param("code") String paramString);
  
  @Select({"select count(*) from ${tableName} where ${pidField} = #{pidValue}"})
  Integer queryChildNode(@Param("tableName") String paramString1, @Param("pidField") String paramString2, @Param("pidValue") String paramString3);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\mapper\OnlCgformHeadMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */