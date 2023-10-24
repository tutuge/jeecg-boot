package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;

import java.util.List;

@Mapper
public interface EcbSteelBandSysDao {

    EcbSteelBand getObject(EcbSteelBand record);

    List<EcbSteelBand> getList(EcbSteelBand record);

    long getCount(EcbSteelBand record);

    int insert(EcbSteelBand record);

    int update(EcbSteelBand record);

    int delete(EcbSteelBand record);
}
