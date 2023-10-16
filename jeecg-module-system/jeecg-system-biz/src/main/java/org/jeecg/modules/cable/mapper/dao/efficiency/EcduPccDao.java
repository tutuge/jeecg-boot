package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcduPcc;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcduPccDao {
    //getObject
    EcduPcc getObject(EcduPcc record);

    //insert
    int insert(EcduPcc record);

    //deletePassEcCompanyIdAndTypeId
    int delete(EcduPcc record);
}
