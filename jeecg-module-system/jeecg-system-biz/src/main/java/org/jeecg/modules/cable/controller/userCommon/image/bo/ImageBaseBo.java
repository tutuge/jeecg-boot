package org.jeecg.modules.cable.controller.userCommon.image.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ImageBaseBo {

    @Schema(description = "主键ID")
    private Integer ecduciId;//主键ID
}
