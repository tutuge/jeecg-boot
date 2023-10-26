package org.jeecg.modules.cable.mapper.dao.efficiency;

import org.jeecg.modules.cable.entity.efficiency.EcdCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcdCollectDao {
    List<EcdCollect> getList(EcdCollect record);
    long getCount(EcdCollect record);
    Integer insert(EcdCollect record);
    Integer update(EcdCollect record);
    EcdCollect getObject(EcdCollect record);
}
