package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EccUnit;

import java.util.List;

public interface EccUnitService {
    EccUnit getObject(EccUnit record);


    EccUnit selectByModelId(Integer ecusmId);

    List<EccUnit> getList(EccUnit record);

    long getCount(EccUnit record);

    Integer insert(EccUnit record);

    Integer update(EccUnit record);

    Integer delete(Integer eccuId);
}
