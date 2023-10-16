package org.jeecg.modules.cable.controller.cert.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CertsBo {

    @Schema(description = "开始类型")
    private String startType;

}
