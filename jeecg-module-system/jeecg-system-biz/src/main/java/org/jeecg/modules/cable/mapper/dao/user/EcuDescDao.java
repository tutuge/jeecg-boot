package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuDescDao {
    EcuDesc getObject(EcuDesc record);

    List<EcuDesc> getList(EcuDesc record);

    long getCount(EcuDesc record);

    int insert(EcuDesc record);

    int update(EcuDesc record);

    int delete(EcuDesc record);

}
