package org.jeecg.modules.cable.mapper.dao.pcc;

import org.jeecg.modules.cable.entity.pcc.EcCounty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcCountyMapper {
    List<EcCounty> getList(EcCounty record);
}
