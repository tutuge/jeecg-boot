package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

import java.util.List;

public interface EcbuStoreService {
    //getList
    List<EcbuStore> getList(EcbuStore record);

    //getCount
    long getCount(EcbuStore record);

    //getObject
    EcbuStore getObject(EcbuStore record);

    //getObjectPassStoreName
    EcbuStore getObjectPassStoreName(EcbuStore record);

    int insert(EcbuStore record);

    int update(EcbuStore record);

    //getLatestObject
    EcbuStore getLatestObject(EcbuStore record);

    int delete(EcbuStore record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuStore> getListGreaterThanSortId(EcbuStore record);

    //updateNotDefaultPassEcCompanyId
    int updateNotDefaultPassEcCompanyId(EcbuStore record);
}
