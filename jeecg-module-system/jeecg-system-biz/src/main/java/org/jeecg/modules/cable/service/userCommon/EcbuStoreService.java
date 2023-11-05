package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

import java.util.List;

public interface EcbuStoreService {

    List<EcbuStore> getList(EcbuStore record);

    //getCount
    long getCount(EcbuStore record);

    //getObject
    EcbuStore getObject(EcbuStore record);

    //getObjectPassStoreName
    EcbuStore getObjectPassStoreName(EcbuStore record);

    Integer insert(EcbuStore record);

    Integer update(EcbuStore record);

    //getLatestObject
    EcbuStore getLatestObject(EcbuStore record);

    Integer delete(EcbuStore record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuStore> getListGreaterThanSortId(EcbuStore record);

    //updateNotDefaultPassEcCompanyId
    Integer updateNotDefaultPassEcCompanyId(EcbuStore record);
}
