package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EccUnit;

import java.util.List;

public interface EccUnitService {
    EccUnit getObject(EccUnit record);

    List<EccUnit> getList(EccUnit record);

    long getCount(EccUnit record);

    int insert(EccUnit record);

    int update(EccUnit record);

    int delete(EccUnit record);
}
