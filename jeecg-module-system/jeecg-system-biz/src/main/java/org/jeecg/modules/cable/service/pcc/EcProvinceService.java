package org.jeecg.modules.cable.service.pcc;

import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.util.List;

public interface EcProvinceService {
    //getList
    List<EcProvince> getList(EcProvince record);
    //getObject
    EcProvince getObject(EcProvince record);
}
