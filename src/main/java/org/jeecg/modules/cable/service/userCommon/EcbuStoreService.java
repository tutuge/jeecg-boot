package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

import java.util.List;

public interface EcbuStoreService {

    List<EcbuStore> getList(EcbuStore record);


    long getCount(EcbuStore record);


    EcbuStore getObject(EcbuStore record);

    EcbuStore getObjectById(Integer ecbusId);

    //getObjectPassStoreName
    EcbuStore getObjectPassStoreName(EcbuStore record);

    Integer insert(EcbuStore record);

    Integer update(EcbuStore record);


    EcbuStore getLatestObject(EcbuStore record);

    Integer delete(EcbuStore record);

    
    List<EcbuStore> getListGreaterThanSortId(EcbuStore record);

    //updateNotDefaultPassEcCompanyId
    Integer updateNotDefaultPassEcCompanyId(EcbuStore record);

    void reduceSort(Integer ecCompanyId, Integer sortId);
}
