package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcuData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuDataMapper {
    EcuData getObject(EcuData record);

    List<EcuData> getList(EcuData record);

    long getCount(EcuData record);

    //insert
    Integer insert(EcuData record);

    Integer update(EcuData record);

}
