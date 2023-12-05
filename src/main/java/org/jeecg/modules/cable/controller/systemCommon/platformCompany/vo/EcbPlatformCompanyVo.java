package org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.cable.entity.systemCommon.EcPlatform;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;


@Schema(description = "系统平台费率Vo")
@Data
@EqualsAndHashCode(callSuper = true)
public class EcbPlatformCompanyVo extends EcbPlatformCompany {

    @Schema(description = "平台类型")
    private EcPlatform ecPlatform;
}
