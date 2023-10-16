package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcdAreaDao {
    List<EcdArea> getList(EcdArea record);

    long getCount(EcdArea record);

    int insert(EcdArea record);

    int update(EcdArea record);

    EcdArea getObject(EcdArea record);
}
