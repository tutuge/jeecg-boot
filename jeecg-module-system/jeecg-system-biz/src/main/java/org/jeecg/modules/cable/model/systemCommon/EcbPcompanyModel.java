package org.jeecg.modules.cable.model.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.service.systemCommon.EcbPcompanyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcbPcompanyModel {
    @Resource
    EcbPcompanyService ecbPcompanyService;

    /***===数据模型===***/
    //getListStart
    public List<EcbPcompany> getListStart() {
        EcbPcompany record = new EcbPcompany();
        record.setStartType(true);
        return ecbPcompanyService.getList(record);
    }
}
