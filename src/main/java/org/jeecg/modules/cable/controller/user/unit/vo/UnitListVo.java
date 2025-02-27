package org.jeecg.modules.cable.controller.user.unit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "单位")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitListVo {
    @Schema(description = "列表")
    private List<UnitVo> list;

    @Schema(description = "数量")
    private Long count;
}
