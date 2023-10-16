package org.jeecg.modules.cable.model.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.service.systemCommon.EcblUnitService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcblUnitModel {
    @Resource
    EcblUnitService ecblUnitService;

    /***===数据模型===***/
    //getListStart
    public List<EcblUnit> getListStart() {
        EcblUnit record = new EcblUnit();
        record.setStartType(true);
        return ecblUnitService.getList(record);
    }
}
