package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.controller.userCommon.qualified.vo.EcuQualifiedVo;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;

@Mapper
public interface EcuQualifiedMapper extends BaseMapper<EcuQualified> {
    EcuQualifiedVo getVoById(@Param("id") Integer id);

    IPage<EcuQualifiedVo> selectPageData(Page<EcuQualified> page, @Param("q") EcuQualified ecuQualified);
}
