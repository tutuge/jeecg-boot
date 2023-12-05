package org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "平台")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbPlatformCompanyDealBo {
    @Schema(description = "主键ID")
    private Integer ecbpId;//主键ID

    @Schema(description = "平台类型ID")
    @NotNull(message = "平台类型ID不得为空")
    private Integer platformId;//平台类型ID

    @Schema(description = "平台名称")
    @NotBlank(message = "平台名称不得为空")
    private String pcName;//平台名称

    @Schema(description = "平台税点")
    @NotNull(message = "平台税点不得为空")
    private BigDecimal pcPercent;//平台税点

    @Schema(description = "备注")
    private String description;//备注
}
