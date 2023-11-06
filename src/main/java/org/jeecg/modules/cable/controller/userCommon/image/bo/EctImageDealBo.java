package org.jeecg.modules.cable.controller.userCommon.image.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "ect图片")
public class EctImageDealBo {

    @NotNull(message = "类型不得为空 1：logo图片 2：印章图片")
    @Schema(description = "类型")
    private Integer typeId;


    @NotBlank(message = "路径不得为空")
    @Schema(description = "路径")
    private String path;
}
