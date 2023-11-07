package org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class EcSilkModelVo extends EcSilkModel {
    @Schema(description = "系统型号")
    private EcSilk ecSilk;
}
