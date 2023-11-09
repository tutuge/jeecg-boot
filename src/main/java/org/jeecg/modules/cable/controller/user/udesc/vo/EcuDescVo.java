package org.jeecg.modules.cable.controller.user.udesc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EcuDescVo extends EcuDesc {

    @Schema(description = "型号信息")
    private List<EcuSilkModel> silkModels;
}
