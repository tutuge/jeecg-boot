package org.jeecg.modules.cable.controller.systemEcable.materials.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;

import java.util.List;

@Schema(description = "材料vo")
@Data
public class MaterialsVo {

    public MaterialsVo() {
    }

    public MaterialsVo(List<EcbMaterials> list, long count) {
        this.list = list;
        this.count = count;
    }

    public MaterialsVo(List<EcbMaterials> list, long count, EcbMaterials record) {
        this.list = list;
        this.count = count;
        this.record = record;
    }

    @Schema(description = "方案列表")
    private List<EcbMaterials> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "材料")
    private EcbMaterials record;
}
