package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EccUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EccUnitDao {
    EccUnit getObject(EccUnit record);

    List<EccUnit> getList(EccUnit record);

    long getCount(EccUnit record);

    Integer insert(EccUnit record);

    Integer update(EccUnit record);

    Integer delete(EccUnit record);
}
