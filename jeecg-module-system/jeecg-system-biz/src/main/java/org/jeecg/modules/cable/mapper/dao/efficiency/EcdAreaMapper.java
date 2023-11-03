package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.efficiency.EcdArea;

import java.util.List;

@Mapper
public interface EcdAreaMapper {
    List<EcdArea> getList(EcdArea record);

    long getCount(EcdArea record);

    Integer insert(EcdArea record);

    Integer update(EcdArea record);

    EcdArea getObject(EcdArea record);
}
