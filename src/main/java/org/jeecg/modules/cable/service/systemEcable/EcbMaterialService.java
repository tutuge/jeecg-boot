package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMaterial;

import java.util.List;

public interface EcbMaterialService {

    List<EcbMaterial> getList(EcbMaterial record);

    List<EcbMaterial> getListStart(EcbMaterial record);

    long getCount();

    EcbMaterial getObject(EcbMaterial record);
}
