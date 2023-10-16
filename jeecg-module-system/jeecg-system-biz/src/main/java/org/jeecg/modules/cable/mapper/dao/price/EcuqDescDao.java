package org.jeecg.modules.cable.mapper.dao.price;

import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuqDescDao {
    //getList
    List<EcuqDesc> getList(EcuqDesc record);

    //getObject
    EcuqDesc getObject(EcuqDesc record);

    //insert
    int insert(EcuqDesc record);

    //deletePassEcuqiId
    void deletePassEcuqiId(int ecuqiId);

    int update(EcuqDesc record);
}
