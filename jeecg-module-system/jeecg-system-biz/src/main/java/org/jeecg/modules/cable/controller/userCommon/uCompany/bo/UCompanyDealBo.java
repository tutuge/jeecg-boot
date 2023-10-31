package org.jeecg.modules.cable.controller.userCommon.uCompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "编辑平台公司")
public class UCompanyDealBo {

    @Schema(description = "主键ID")
    private Integer ecducId;//主键ID


    @Schema(description = "公司简称")
    @NotBlank(message = "公司简称不得为空")
    private String abbreviation;//公司简称

    @Schema(description = "公司全称")
    @NotBlank(message = "公司全称不得为空")
    private String fullName;//公司全称

    @Schema(description = "logo图片")
    private String logoImg;//logo图片

    @Schema(description = "发票税点类型")
    @NotNull(message = "发票税点类型不得为空")
    private Integer billPercentType;//发票税点类型

    @Schema(description = "备注")
    @NotBlank(message = "备注不得为空")
    private String description;//备注
}
