package org.jeecg.modules.cable.mapper.dao.pcc;

import org.jeecg.modules.cable.entity.pcc.EcCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcCityMapper {
    List<EcCity> getList(EcCity record);
}
