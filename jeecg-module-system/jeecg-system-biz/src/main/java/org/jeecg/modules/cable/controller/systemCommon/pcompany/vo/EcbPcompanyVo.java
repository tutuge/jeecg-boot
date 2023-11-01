package org.jeecg.modules.cable.controller.systemCommon.pcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemCommon.EcPlatform;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;


@Schema(description = "系统平台费率Vo")
@Data
public class EcbPcompanyVo extends EcbPcompany {

    @Schema(description = "平台类型")
    private EcPlatform ecPlatform;
}
