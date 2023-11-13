package org.jeecg.modules.cable.controller.userCommon.qualified.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "合格证VO")
public class EcuQualifiedVo extends EcuQualified {

    @Schema(description = "用户型号")
    private SilkModelVo silkModelVo;
}
