package org.jeecg.modules.cable.controller.user.unit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

import java.util.List;

@Schema(description = "单位vo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnitVo extends EccUnit {


    @Schema(description = "型号")
    private List<EcuSilkModel> silkModels;

}
