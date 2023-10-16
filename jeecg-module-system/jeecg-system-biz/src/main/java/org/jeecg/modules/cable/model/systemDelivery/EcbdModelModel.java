package org.jeecg.modules.cable.model.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.service.systemDelivery.EcbdModelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EcbdModelModel {
    @Resource
    EcbdModelService ecbdModelService;

    /***===数据模型===***/
    //getObjectPassEcbdId
    public EcbdModel getObjectPassEcbdId(int ecbdId) {
        EcbdModel record = new EcbdModel();
        record.setEcbdId(ecbdId);
        return ecbdModelService.getObject(record);
    }
}
