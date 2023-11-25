package org.jeecg.modules.cable.controller.systemQuality.level.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LevelVo")
@Data
public class SystemLevelVo {

    @Schema(description = "list")
    private List<EcqLevel> list;

    @Schema(description = "count")
    private Long count;
}
