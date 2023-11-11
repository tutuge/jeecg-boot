package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;

import java.util.List;

@Mapper
public interface EcbuDeliveryMapper {

    List<EcbuDelivery> getList(EcbuDelivery record);


    long getCount(EcbuDelivery record);


    EcbuDelivery getObject(EcbuDelivery record);

    // insert
    Integer insert(EcbuDelivery record);

    Integer update(EcbuDelivery record);

    Integer delete(EcbuDelivery record);

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuDelivery> getListGreaterThanSortId(EcbuDelivery record);

    // getObjectPassDeliveryName
    EcbuDelivery getObjectPassDeliveryName(EcbuDelivery record);


    EcbuDelivery getLatestObject(EcbuDelivery record);

    void reduceSort(@Param("ecCompanyId") Integer ecCompanyId, @Param("sortId") Integer sortId);
}
