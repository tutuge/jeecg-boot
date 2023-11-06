package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcduciPositionMapper {
    EcduciPosition getObject(EcduciPosition record);

    Integer insert(EcduciPosition record);

    Integer delete(EcduciPosition record);

    Integer update(EcduciPosition record);
}
