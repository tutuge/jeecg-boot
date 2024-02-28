package org.jeecg.modules.cable.controller.userEcable.programme.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "编辑结构中的名称、重量和金额")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialVo {


    @Schema(description = "材料名称")
    private String fullName;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "金额")
    private BigDecimal money;

}
