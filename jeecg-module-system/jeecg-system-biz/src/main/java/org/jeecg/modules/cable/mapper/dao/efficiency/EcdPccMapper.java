package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.efficiency.EcdPcc;

@Mapper
public interface EcdPccMapper {
    EcdPcc getObject(EcdPcc record);

    Integer insert(EcdPcc record);

    Integer delete(EcdPcc record);
}
