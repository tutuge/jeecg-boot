package org.jeecg.modules.cable.controller.userCommon.utaxpoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "系统税点列表")
public class UTaxPointVo {

    @Schema(description = "系统税点列表")
    private List<EcduTaxPoint> list;

    @Schema(description = "数量")
    private Long count;
}
