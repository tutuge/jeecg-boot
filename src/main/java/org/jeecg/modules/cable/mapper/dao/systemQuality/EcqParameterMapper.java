package org.jeecg.modules.cable.mapper.dao.systemQuality;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemQuality.EcqParameter;

import java.util.List;

/**
 * 系统质量等级参数
 */
@Mapper
public interface EcqParameterMapper extends BaseMapper<EcqParameter> {

    List<EcqParameter> getList(EcqParameter record);

    long getCount(EcqParameter record);

    EcqParameter getObject(EcqParameter record);

    List<EcqParameter> getListGreaterThanSortId(EcqParameter record);

    EcqParameter getObjectPassEcqulIdAndEcbusId(EcqParameter record);

    EcqParameter getLatestObject(EcqParameter record);
}
