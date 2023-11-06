package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbusAttributeMapper {
    EcbusAttribute getObject(EcbusAttribute record);

    Integer insert(EcbusAttribute record);

    Integer update(EcbusAttribute record);
}
