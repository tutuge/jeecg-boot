package org.jeecg.modules.cable.mapper.dao.systemPcc;

import org.jeecg.modules.cable.entity.systemPcc.EcCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcCityMapper {
    List<EcCity> getList(EcCity record);
}
