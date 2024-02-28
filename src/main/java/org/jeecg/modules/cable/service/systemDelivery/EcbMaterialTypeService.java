package org.jeecg.modules.cable.service.systemDelivery;

import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;

import java.util.List;

public interface EcbMaterialTypeService {

    List<EcbMaterialType> getList(EcbMaterialType record);
}
