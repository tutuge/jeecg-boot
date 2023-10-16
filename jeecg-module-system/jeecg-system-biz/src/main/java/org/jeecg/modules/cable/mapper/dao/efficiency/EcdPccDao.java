package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdPcc;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcdPccDao {
    EcdPcc getObject(EcdPcc record);

    int insert(EcdPcc record);

    int delete(EcdPcc record);
}
