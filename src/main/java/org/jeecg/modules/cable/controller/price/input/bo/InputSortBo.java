package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "input")
@Data
public class InputSortBo {

    @Schema(description = "主键ID")
    private Integer ecuqiId;

    @Schema(description = "排序")
    private Integer sortId;
}
