package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcduciPositionDao {
    EcduciPosition getObject(EcduciPosition record);

    int insert(EcduciPosition record);

    int delete(EcduciPosition record);

    int update(EcduciPosition record);
}
