package org.jeecg.modules.cable.service.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;

import java.util.List;

public interface EcbMaterialService {

    List<EcbMaterialType> getList(EcbMaterialType record);

    List<EcbMaterialType> getListStart(EcbMaterialType record);

    long getCount();

    EcbMaterialType getObject(EcbMaterialType record);
}
