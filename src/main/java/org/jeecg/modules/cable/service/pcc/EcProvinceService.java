package org.jeecg.modules.cable.service.pcc;

import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.util.List;

public interface EcProvinceService {

    List<EcProvince> getList(EcProvince record);

    EcProvince getObject(EcProvince record);


    EcProvince getObjectById(Integer ecpId);
}
