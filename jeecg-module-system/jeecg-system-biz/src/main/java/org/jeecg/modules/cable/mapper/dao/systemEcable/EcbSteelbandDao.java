package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbSteelbandDao {
    List<EcbSteelband> getList(EcbSteelband record);//获取数据列表

    List<EcbSteelband> getListStart(EcbSteelband record);

    long getCount();//获取总数

    EcbSteelband getObject(EcbSteelband record);//根据EcbSteelband获取EcbSteelband
}
