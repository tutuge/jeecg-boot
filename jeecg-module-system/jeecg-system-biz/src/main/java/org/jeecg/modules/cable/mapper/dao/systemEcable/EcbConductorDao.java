package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbConductorDao {
    List<EcbConductor> getList(EcbConductor record);//获取数据列表

    List<EcbConductor> getListStart(EcbConductor record);//获取启用列表

    long getCount();//获取总数

    EcbConductor getObject(EcbConductor record);//根据EcbConductor获取EcbConductor
}
