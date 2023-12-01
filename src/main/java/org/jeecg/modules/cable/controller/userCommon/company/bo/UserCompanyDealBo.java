package org.jeecg.modules.cable.controller.userCommon.company.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "编辑平台公司")
public class UserCompanyDealBo {

    @Schema(description = "主键ID")
    private Integer ecducId;


    @Schema(description = "公司简称")
    @NotBlank(message = "公司简称不得为空")
    private String abbreviation;

    @Schema(description = "公司全称")
    @NotBlank(message = "公司全称不得为空")
    private String fullName;

    @Schema(description = "logo图片")
    private String logoImg;

    @Schema(description = "发票税点类型")
    @NotNull(message = "发票税点类型不得为空")
    private Integer billPercentType;

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;
}
