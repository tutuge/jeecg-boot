package org.jeecg.modules.cable.controller.user.udesc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "备注管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuDescBo {

    @Schema(description = "主键ID")
    private Integer ecudId;

    @Schema(description = "是否默认")
    private Boolean defaultType;//是否默认
}
