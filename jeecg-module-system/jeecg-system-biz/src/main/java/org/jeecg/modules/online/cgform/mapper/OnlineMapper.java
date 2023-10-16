package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OnlineMapper {
    List<Map<String, Object>> selectByCondition(@Param("sqlStr") String paramString, @Param("param") Map<String, Object> paramMap);

    IPage<Map<String, Object>> selectPageByCondition(Page<Map<String, Object>> paramPage, @Param("sqlStr") String paramString, @Param("param") Map<String, Object> paramMap);
}
