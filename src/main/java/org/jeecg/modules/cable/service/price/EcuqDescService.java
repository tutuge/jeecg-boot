package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.util.List;

public interface EcuqDescService {
    List<EcuqDesc> getList(EcuqDesc record);

    EcuqDesc getObject(EcuqDesc record);

    Integer insert(EcuqDesc record);

    void deletePassEcuqiId(Integer ecuqiId);

    Integer update(EcuqDesc record);
}
