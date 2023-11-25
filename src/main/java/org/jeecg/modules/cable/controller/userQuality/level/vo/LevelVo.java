package org.jeecg.modules.cable.controller.userQuality.level.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LevelVo")
@Data
public class LevelVo {

    @Schema(description = "list")
    private List<EcquLevel> list;

    @Schema(description = "count")
    private  Long count;
}
