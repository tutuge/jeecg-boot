package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuStoreDao {
    //getList
    List<EcbuStore> getList(EcbuStore record);

    //getCount
    long getCount(EcbuStore record);

    //getObject
    EcbuStore getObject(EcbuStore record);

    //getObjectPassStoreName
    EcbuStore getObjectPassStoreName(EcbuStore record);

    //insert
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
