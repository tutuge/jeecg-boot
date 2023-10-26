package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcduTaxpointDao {
    //getList
    List<EcduTaxpoint> getList(EcduTaxpoint record);

    //getCount
    long getCount(EcduTaxpoint record);

    //getObject
    EcduTaxpoint getObject(EcduTaxpoint record);

    //insert
    Integer insert(EcduTaxpoint record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcduTaxpoint record);

    //deletePassEcCompanyIdAndEcdtId
    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxpoint record);
}
