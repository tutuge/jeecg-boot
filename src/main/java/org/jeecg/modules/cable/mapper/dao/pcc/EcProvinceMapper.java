package org.jeecg.modules.cable.mapper.dao.pcc;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.util.List;

@Mapper
public interface EcProvinceMapper {

    List<EcProvince> getList(EcProvince record);


    EcProvince getObject(EcProvince record);
}
