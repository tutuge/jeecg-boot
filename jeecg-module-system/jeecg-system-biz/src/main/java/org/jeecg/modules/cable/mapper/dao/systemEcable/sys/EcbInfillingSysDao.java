package org.jeecg.modules.cable.mapper.dao.systemEcable.sys;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;

import java.util.List;

@Mapper
public interface EcbInfillingSysDao {

    EcbInfilling getObject(EcbInfilling record);

    List<EcbInfilling> getList(EcbInfilling record);

    long getCount(EcbInfilling record);

    Integer insert(EcbInfilling record);

    Integer update(EcbInfilling record);

    Integer delete(EcbInfilling record);
}
