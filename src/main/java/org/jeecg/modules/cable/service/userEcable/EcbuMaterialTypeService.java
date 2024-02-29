package org.jeecg.modules.cable.service.userEcable;

import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialDealBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialBaseBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialListBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialSortBo;
import org.jeecg.modules.cable.controller.userEcable.material.vo.MaterialListVo;
import org.jeecg.modules.cable.controller.userEcable.material.vo.MaterialTypeVo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;

import java.util.List;

public interface EcbuMaterialTypeService {

    List<EcbuMaterialType> getList(EcbuMaterialType record);

    MaterialTypeVo getList(EcbuMaterialListBo bo);


    EcbuMaterialType getById(EcbuMaterialBaseBo bo);


    EcbuMaterialType getObject(EcbuMaterialType type);


    String saveOrUpdate(EcbuMaterialDealBo bo);


    void sort(List<EcbuMaterialSortBo> bos);


    String start(EcbuMaterialBaseBo bo);


    void delete(EcbuMaterialBaseBo bo);


    // getObjectPassEcbbId
    EcbuMaterialType getObjectPassId(Integer id);

    List<MaterialListVo> getMaterialList();
}
