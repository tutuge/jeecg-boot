package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "修改丝名称的别名")
@Data
public class InputSilkNameAsBo {


    @Schema(description = "主键ID")
    private Integer ecuqiId;

    @Schema(description = "丝名称别名")
    private String silkNameAs;
}
