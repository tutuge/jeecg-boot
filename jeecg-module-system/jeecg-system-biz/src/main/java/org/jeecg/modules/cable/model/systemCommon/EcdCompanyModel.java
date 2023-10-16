package org.jeecg.modules.cable.model.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.service.systemCommon.EcdCompanyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcdCompanyModel {
    @Resource
    EcdCompanyService ecdCompanyService;

    /***===数据模型===***/
    public List<EcdCompany> getListStart() {
        EcdCompany record = new EcdCompany();
        record.setStartType(true);
        return ecdCompanyService.getList(record);
    }
}
