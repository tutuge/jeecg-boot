package org.jeecg.modules.cable.controller.pcc.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcdPccBo {

    @Schema(description = "类型 1 快递使用")
    private Integer typeId;//类型 1 快递使用
}
