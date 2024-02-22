package org.jeecg.modules.cable.controller.userEcable.material.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;

import java.util.ArrayList;
import java.util.List;


@Schema(description = "用户基础材料名称vo")
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialListVo extends EcbuMaterialType {

    @Schema(description = "材料")
    private List<EcbuMaterials> materialsList = new ArrayList<>();

}
