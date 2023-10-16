package org.jeecg.modules.cable.service.price;

import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.util.List;

public interface EcuqDescService {
    List<EcuqDesc> getList(EcuqDesc record);

    EcuqDesc getObject(EcuqDesc record);

    int insert(EcuqDesc record);

    void deletePassEcuqiId(int ecuqiId);

    int update(EcuqDesc record);
}
