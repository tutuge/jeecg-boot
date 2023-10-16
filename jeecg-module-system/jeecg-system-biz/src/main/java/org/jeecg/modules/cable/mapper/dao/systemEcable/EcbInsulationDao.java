package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbInsulationDao {
    List<EcbInsulation> getList(EcbInsulation record);//获取数据列表

    List<EcbInsulation> getListStart(EcbInsulation record);//获取启用列表

    long getCount();//获取总数

    EcbInsulation getObject(EcbInsulation record);//根据ecbiId获取EcbInsulation
}
