package org.jeecg.modules.cable.domain.computeBo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class Structure {
    @Schema(description = "导体")
    private Conductor conductor;

    @Schema(description = "内部材料")
    private List<Internal> internals;

    @Schema(description = "填充物")
    private Infilling infilling;

    @Schema(description = "外部材料")
    private List<External> externals;
}
