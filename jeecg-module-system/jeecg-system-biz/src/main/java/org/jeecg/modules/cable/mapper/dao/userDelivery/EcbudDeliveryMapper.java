package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbudDeliveryMapper {
    //getObject
    EcbudDelivery getObject(EcbudDelivery record);

    //insert
    Integer insert(EcbudDelivery record);

    //updateByPrimaryKeySelective
    Integer updateByPrimaryKeySelective(EcbudDelivery record);
}
