package org.jeecg.modules.cable.controller.userCommon.image.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ImageDealBo {

    @NotNull(message = "公司ID不得为空")
    @Schema(description = "公司ID")
    private Integer ecducId;// 公司ID

    @NotBlank(message = "图片地址不得为空")
    @Schema(description = "图片地址")
    private String path;
}
