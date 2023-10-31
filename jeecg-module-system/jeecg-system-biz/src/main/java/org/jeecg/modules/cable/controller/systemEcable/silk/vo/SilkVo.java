package org.jeecg.modules.cable.controller.systemEcable.silk.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.systemEcable.*;


@Schema(description = "丝型号vo")
@Data
@EqualsAndHashCode(callSuper = true)
public class SilkVo extends EcSilk {

    @Schema(description = "系统包带")
    private EcbBag ecbBag;

    @Schema(description = "系统导体")
    private EcbConductor ecbConductor;

    @Schema(description = "系统填充物")
    private EcbInfilling ecbInfilling;

    @Schema(description = "系统绝缘")
    private EcbInsulation ecbInsulation;

    @Schema(description = "系统云母带")
    private EcbMicatape ecbMicatape;

    @Schema(description = "系统护套")
    private EcbSheath ecbSheath;

    @Schema(description = "系统屏蔽")
    private EcbShield ecbShield;

    @Schema(description = "系统钢带")
    private EcbSteelBand ecbSteelBand;

}
