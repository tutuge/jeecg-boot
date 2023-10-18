package org.jeecg.modules.cable.controller.quality.uarea.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.quality.EcuArea;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "截面列表")
public class UAreaVo {

    @Schema(description = "截面列表")
    private List<EcuArea> list;

    @Schema(description = "数量")
    private Long count;
}
