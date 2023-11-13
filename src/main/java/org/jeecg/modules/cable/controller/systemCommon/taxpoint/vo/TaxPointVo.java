package org.jeecg.modules.cable.controller.systemCommon.taxpoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "系统税点列表")
public class TaxPointVo {

    @Schema(description = "系统税点列表")
    private List<EcdTaxPoint> list;

    @Schema(description = "数量")
    private Long count;
}
