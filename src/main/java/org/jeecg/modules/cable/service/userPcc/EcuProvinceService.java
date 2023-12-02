package org.jeecg.modules.cable.service.userPcc;

import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.entity.userPcc.EcuProvince;

import java.util.List;

public interface EcuProvinceService {

    void batchInsert(List<EcProvince> listProvince,Integer ecCompanyId);

    List<EcuProvince> selectByCompanyId(Integer ecCompanyId);

    List<EcuProvince> selectPccByCompanyId(Integer ecCompanyId);

    EcuProvince insertProvinceName(String provinceName, Integer ecCompanyId);

    void updateProvinceName(String provinceName, Integer ecpId, Integer ecCompanyId);

    void deleteByEcpId(Integer ecpId, Integer ecCompanyId);
}
