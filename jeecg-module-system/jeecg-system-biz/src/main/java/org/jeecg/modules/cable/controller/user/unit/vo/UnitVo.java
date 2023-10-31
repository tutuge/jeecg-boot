package org.jeecg.modules.cable.controller.user.unit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.user.EccUnit;

import java.util.List;

@Schema(description = "单位vo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitVo extends EccUnit {


    @Schema(description = "型号")
    private List<EcSilk> silks;

}
