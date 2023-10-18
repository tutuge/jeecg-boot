package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "备注添加")
@Data
public class InputBo {


    @Schema(description = "主键ID")
    private Integer ecuqiId;
}
