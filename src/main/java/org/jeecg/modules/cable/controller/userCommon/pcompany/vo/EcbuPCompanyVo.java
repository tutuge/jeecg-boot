package org.jeecg.modules.cable.controller.userCommon.pcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcPlatform;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户平台费率")
public class EcbuPCompanyVo extends EcbuPlatformCompany {
    @Schema(description = "平台类型")
    private EcPlatform ecPlatform;
}
