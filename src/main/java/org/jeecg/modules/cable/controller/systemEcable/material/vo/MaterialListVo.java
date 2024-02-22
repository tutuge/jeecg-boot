package org.jeecg.modules.cable.controller.systemEcable.material.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;

import java.util.ArrayList;
import java.util.List;


@Schema(description = "系统基础材料名称vo")
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialListVo extends EcbMaterialType {

    @Schema(description = "材料")
    private List<EcbMaterials> materialsList = new ArrayList<>();

}
