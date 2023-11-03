package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.efficiency.EcduPcc;

@Mapper
public interface EcduPccMapper {
    //getObject
    EcduPcc getObject(EcduPcc record);

    //insert
    Integer insert(EcduPcc record);

    //deletePassEcCompanyIdAndTypeId
    Integer delete(EcduPcc record);
}
