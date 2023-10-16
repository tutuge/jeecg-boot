package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbudDeliveryDao {
    //getObject
    EcbudDelivery getObject(EcbudDelivery record);

    //insert
    int insert(EcbudDelivery record);

    //updateByPrimaryKeySelective
    int updateByPrimaryKeySelective(EcbudDelivery record);
}
