package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

import java.util.List;

@Mapper
public interface EcbuStoreMapper extends BaseMapper<EcbuStore> {

    List<EcbuStore> getList(EcbuStore record);


    long getCount(EcbuStore record);


    EcbuStore getObject(EcbuStore record);

    //getObjectPassStoreName
    EcbuStore getObjectPassStoreName(EcbuStore record);

    Integer updateRecord(EcbuStore record);


    EcbuStore getLatestObject(EcbuStore record);

    Integer deleteByIdOrCompanyId(EcbuStore record);


    List<EcbuStore> getListGreaterThanSortId(EcbuStore record);

    //updateNotDefaultPassEcCompanyId
    Integer updateNotDefaultPassEcCompanyId(EcbuStore record);

    void reduceSort(Integer ecCompanyId, Integer sortId);
}
