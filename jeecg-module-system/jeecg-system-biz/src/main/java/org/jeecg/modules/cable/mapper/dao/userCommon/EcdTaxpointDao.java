package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.systemEcable.EcdTaxpoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcdTaxpointDao {
    //getList
    List<EcdTaxpoint> getList(EcdTaxpoint record);

    //getCount
    long getCount(EcdTaxpoint record);

    //getObject
    EcdTaxpoint getObject(EcdTaxpoint record);
}
