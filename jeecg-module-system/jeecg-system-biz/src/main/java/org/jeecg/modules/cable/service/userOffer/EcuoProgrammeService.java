package org.jeecg.modules.cable.service.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;

import java.util.List;

public interface EcuoProgrammeService {
    List<EcuoProgramme> getList(EcuoProgramme record);

    EcuoProgramme getObject(EcuoProgramme record);

    int insert(EcuoProgramme record);

    int update(EcuoProgramme record);

    int delete(EcuoProgramme record);
}
