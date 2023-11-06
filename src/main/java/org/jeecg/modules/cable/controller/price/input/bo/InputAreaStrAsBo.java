package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "修改截面积名称的别名")
@Data
public class InputAreaStrAsBo {


    @Schema(description = "主键ID")
    private Integer ecuqiId;

    @Schema(description = "截面积别名")
    private String areaStrAs;
}
