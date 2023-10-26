package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;

import java.util.List;

public interface EcuoProgrammeService {
    List<EcuoProgramme> getList(EcuoProgramme record);

    EcuoProgramme getObject(EcuoProgramme record);

    Integer insert(EcuoProgramme record);

    Integer update(EcuoProgramme record);

    Integer delete(EcuoProgramme record);
}
