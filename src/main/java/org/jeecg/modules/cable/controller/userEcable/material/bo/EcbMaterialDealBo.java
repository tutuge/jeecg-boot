package org.jeecg.modules.cable.controller.userEcable.material.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "系统基础材料名称")
@Data
public class EcbMaterialDealBo {


    @Schema(description = "系统基础材料名称ID")
    private Integer id;

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空")
    private String fullName;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    @NotNull(message = "材料类型不得为空")
    private Integer materialType;

    @Schema(description = "说明")
    private String description = "";
}
