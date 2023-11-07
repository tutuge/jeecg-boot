package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcduTaxPointMapper {

    List<EcduTaxPoint> getList(EcduTaxPoint record);


    long getCount(EcduTaxPoint record);


    EcduTaxPoint getObject(EcduTaxPoint record);


    Integer insert(EcduTaxPoint record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcduTaxPoint record);

    //deletePassEcCompanyIdAndEcdtId
    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record);
}
