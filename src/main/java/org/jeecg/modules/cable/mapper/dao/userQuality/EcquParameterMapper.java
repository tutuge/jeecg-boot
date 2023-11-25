package org.jeecg.modules.cable.mapper.dao.userQuality;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.userQuality.EcquParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcquParameterMapper extends BaseMapper<EcquParameter> {

    List<EcquParameter> getList(EcquParameter record);

    long getCount(EcquParameter record);

    EcquParameter getObject(EcquParameter record);
    
    List<EcquParameter> getListGreaterThanSortId(EcquParameter record);
    //getObjectPassEcqulIdAndEcbusId
    EcquParameter getObjectPassEcqulIdAndEcbusId(EcquParameter record);

    EcquParameter getLatestObject(EcquParameter record);
}
