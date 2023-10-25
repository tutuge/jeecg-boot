package org.jeecg.modules.cable.controller.userCommon.store.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EcbuStoreBaseBo {

    @Schema(description = "主键ID")
    private Integer ecbusId;//主键ID
}
