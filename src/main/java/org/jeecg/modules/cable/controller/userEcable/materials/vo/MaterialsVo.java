package org.jeecg.modules.cable.controller.userEcable.materials.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;

import java.util.List;

@Schema(description = "材料vo")
@Data
public class MaterialsVo {

    public MaterialsVo() {
    }

    public MaterialsVo(List<EcbuMaterials> list, long count) {
        this.list = list;
        this.count = count;
    }

    public MaterialsVo(List<EcbuMaterials> list, long count, EcbuMaterials record) {
        this.list = list;
        this.count = count;
        this.record = record;
    }

    @Schema(description = "方案列表")
    private List<EcbuMaterials> list;

    @Schema(description = "数量")
    private long count;

    @Schema(description = "材料")
    private EcbuMaterials record;
}
