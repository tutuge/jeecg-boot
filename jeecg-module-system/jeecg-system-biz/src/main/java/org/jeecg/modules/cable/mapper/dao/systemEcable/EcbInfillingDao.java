package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbInfillingDao {
    List<EcbInfilling> getList(EcbInfilling record);//获取数据列表

    List<EcbInfilling> getListStart(EcbInfilling record);

    long getCount();//获取总数

    EcbInfilling getObject(EcbInfilling record);//根据EcbInfilling获取EcbInfilling
}
