package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuStoreMapper {

    List<EcbuStore> getList(EcbuStore record);


    long getCount(EcbuStore record);


    EcbuStore getObject(EcbuStore record);

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
