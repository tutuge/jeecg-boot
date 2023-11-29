package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;

import java.util.List;

@Mapper
public interface EcbStoreMapper extends BaseMapper<EcbStore> {

    List<EcbStore> getList(EcbStore record);


    long getCount(EcbStore record);


    EcbStore getObject(EcbStore record);

    //getObjectPassStoreName
    EcbStore getObjectPassStoreName(EcbStore record);

    Integer updateRecord(EcbStore record);


    EcbStore getLatestObject(EcbStore record);

    Integer deleteByIdOrCompanyId(EcbStore record);


    List<EcbStore> getListGreaterThanSortId(EcbStore record);

    void reduceSort(Integer sortId);

    void updateNotDefault();
}
