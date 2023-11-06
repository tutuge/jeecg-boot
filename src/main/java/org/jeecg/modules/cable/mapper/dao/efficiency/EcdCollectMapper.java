package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.efficiency.EcdCollect;

import java.util.List;

@Mapper
public interface EcdCollectMapper {
    List<EcdCollect> getList(EcdCollect record);

    long getCount(EcdCollect record);

    Integer insert(EcdCollect record);

    Integer update(EcdCollect record);

    EcdCollect getObject(EcdCollect record);
}
