package org.jeecg.modules.cable.controller.userCommon.image.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ImageBaseBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecduciId;//主键ID
}
