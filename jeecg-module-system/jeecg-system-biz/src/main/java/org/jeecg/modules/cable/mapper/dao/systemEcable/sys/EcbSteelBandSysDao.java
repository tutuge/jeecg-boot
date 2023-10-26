package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;

import java.util.List;

@Mapper
public interface EcbSteelBandSysDao {

    EcbSteelBand getObject(EcbSteelBand record);

    List<EcbSteelBand> getList(EcbSteelBand record);

    long getCount(EcbSteelBand record);

    Integer insert(EcbSteelBand record);

    Integer update(EcbSteelBand record);

    Integer delete(EcbSteelBand record);
}
