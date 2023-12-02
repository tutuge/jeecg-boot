package org.jeecg.modules.cable.controller.price.input.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "根据丝型号获取默认的质量等级")
@Data
public class InputSilkNameBo {

    @Schema(description = "主键ID")
    private Integer ecqulId;

    @Schema(description = "型号名称")
    private String silkName;
}
