package org.jeecg.modules.cable.mapper.dao.price;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.util.List;

@Mapper
public interface EcuqDescMapper {

    List<EcuqDesc> getList(EcuqDesc record);


    EcuqDesc getObject(EcuqDesc record);


    Integer insert(EcuqDesc record);

    //deletePassEcuqiId
    void deletePassEcuqiId(Integer ecuqiId);

    Integer update(EcuqDesc record);
}
