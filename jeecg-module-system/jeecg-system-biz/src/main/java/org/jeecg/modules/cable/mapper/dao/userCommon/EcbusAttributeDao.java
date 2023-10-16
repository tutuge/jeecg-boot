package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbusAttribute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbusAttributeDao {
    EcbusAttribute getObject(EcbusAttribute record);

    int insert(EcbusAttribute record);

    int update(EcbusAttribute record);
}
