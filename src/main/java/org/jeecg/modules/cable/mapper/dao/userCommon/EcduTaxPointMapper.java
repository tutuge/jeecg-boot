package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcduTaxPointMapper {

    List<EcduTaxPoint> getList(EcduTaxPoint record);

    //getCount
    long getCount(EcduTaxPoint record);

    //getObject
    EcduTaxPoint getObject(EcduTaxPoint record);

    //insert
    Integer insert(EcduTaxPoint record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcduTaxPoint record);

    //deletePassEcCompanyIdAndEcdtId
    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record);
}
