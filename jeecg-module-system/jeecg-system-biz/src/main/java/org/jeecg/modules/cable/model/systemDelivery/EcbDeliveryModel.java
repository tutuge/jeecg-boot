package org.jeecg.modules.cable.model.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.jeecg.modules.cable.service.systemDelivery.EcbDeliveryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcbDeliveryModel {
    @Resource
    EcbDeliveryService ecbDeliveryService;

    /***===数据模型===***/
    //getListStart
    public List<EcbDelivery> getListStart() {
        EcbDelivery record = new EcbDelivery();
        record.setStartType(true);
        return ecbDeliveryService.getList(record);
    }

    //getObjectPassDeliveryName
    public EcbDelivery getObjectPassDeliveryName(String deliveryName) {
        EcbDelivery record = new EcbDelivery();
        record.setDeliveryName(deliveryName);
        return ecbDeliveryService.getObject(record);
    }
}
