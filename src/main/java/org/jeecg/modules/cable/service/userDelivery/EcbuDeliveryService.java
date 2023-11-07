package org.jeecg.modules.cable.service.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;

import java.util.List;

public interface EcbuDeliveryService {
    List<EcbuDelivery> getList(EcbuDelivery record);

    long getCount(EcbuDelivery record);

    EcbuDelivery getObject(EcbuDelivery record);

    Integer insert(EcbuDelivery record);

    Integer update(EcbuDelivery record);

    Integer delete(EcbuDelivery record);

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuDelivery> getListGreaterThanSortId(EcbuDelivery record);

    // getObjectPassDeliveryName
    EcbuDelivery getObjectPassDeliveryName(EcbuDelivery record);


    EcbuDelivery getLatestObject(EcbuDelivery record);
}
