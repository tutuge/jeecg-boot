package org.jeecg.modules.cable.controller.user.udesc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "报价单明细备注")
public class UDescListVo {

    @Schema(description = "报价明细")
    private List<EcuDescVo> list;

    @Schema(description = "数量")
    private Long count;
}
