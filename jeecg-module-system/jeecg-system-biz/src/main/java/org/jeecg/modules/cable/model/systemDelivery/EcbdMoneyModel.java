package org.jeecg.modules.cable.model.systemDelivery;

import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;
import org.jeecg.modules.cable.service.systemDelivery.EcbdMoneyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcbdMoneyModel {
    @Resource
    EcbdMoneyService ecbdMoneyService;

    /***===数据模型===***/
    //getListPassEcbdId
    public List<EcbdMoney> getListPassEcbdId(int ecbdId) {
        EcbdMoney record = new EcbdMoney();
        record.setEcbdId(ecbdId);
        return ecbdMoneyService.getList(record);
    }
}
