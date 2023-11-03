package org.jeecg.modules.cable.mapper.dao.standard;

import org.jeecg.modules.cable.entity.standard.EcStandard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcStandardMapper {
    //getObject
    EcStandard getObject(EcStandard record);
}
