package org.jeecg.modules.cable.controller.userCommon.position.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "根据ID查询图片位置")
public class PositionBo {

    @Schema(description = "图片ID")
    private Integer ecduciId;
}
