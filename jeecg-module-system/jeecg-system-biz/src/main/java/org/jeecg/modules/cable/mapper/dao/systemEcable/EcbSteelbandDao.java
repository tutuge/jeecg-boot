package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbSteelbandDao {
    List<EcbSteelBand> getList(EcbSteelBand record);//获取数据列表

    List<EcbSteelBand> getListStart(EcbSteelBand record);

    long getCount();//获取总数

    EcbSteelBand getObject(EcbSteelBand record);//根据EcbSteelband获取EcbSteelband
}
