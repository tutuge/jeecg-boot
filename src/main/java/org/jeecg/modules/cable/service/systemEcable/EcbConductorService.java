package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;

import java.util.List;

public interface EcbConductorService {
    List<EcbMaterials> getList(EcbMaterials record);

    List<EcbMaterials> getListStart(EcbMaterials record);

    long getCount();

    EcbMaterials getObject(EcbMaterials record);
}
