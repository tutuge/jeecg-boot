package org.jeecg.modules.cable.controller.userEcable.SilkModel.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class SilkModelVo extends EcuSilkModel {

    @Schema(description = "型号类型")
    private EcuSilk ecuSilk;
}
