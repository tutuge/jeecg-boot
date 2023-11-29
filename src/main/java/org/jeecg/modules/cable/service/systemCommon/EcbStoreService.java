package org.jeecg.modules.cable.service.systemCommon;


import org.jeecg.modules.cable.entity.systemCommon.EcbStore;

import java.util.List;

public interface EcbStoreService {

    List<EcbStore> getList(EcbStore record);

    long getCount(EcbStore record);


    EcbStore getObject(EcbStore record);

    EcbStore getObjectById(Integer ecbusId);

    //getObjectPassStoreName
    EcbStore getObjectPassStoreName(EcbStore record);

    Integer insert(EcbStore record);

    Integer update(EcbStore record);


    EcbStore getLatestObject(EcbStore record);

    Integer delete(EcbStore record);


    List<EcbStore> getListGreaterThanSortId(EcbStore record);


    void reduceSort(Integer sortId);

    void updateNotDefault();
}
