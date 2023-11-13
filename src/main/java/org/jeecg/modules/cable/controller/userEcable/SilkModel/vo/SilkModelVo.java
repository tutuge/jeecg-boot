package org.jeecg.modules.cable.controller.userEcable.SilkModel.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SilkModelVo extends EcuSilkModel {

    @Schema(description = "型号类型")
    private EcuSilk ecuSilk;
}
