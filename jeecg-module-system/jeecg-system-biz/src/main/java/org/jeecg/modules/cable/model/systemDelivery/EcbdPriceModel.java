package org.jeecg.modules.cable.model.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;
import org.jeecg.modules.cable.service.systemDelivery.EcbdPriceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcbdPriceModel {
    @Resource
    EcbdPriceService ecbdPriceService;

    /***===数据模型===***/
    //getListPassEcbdId
    public List<EcbdPrice> getListPassEcbdId(int ecbdId) {
        EcbdPrice record = new EcbdPrice();
        record.setEcbdId(ecbdId);
        return ecbdPriceService.getList(record);
    }
}
