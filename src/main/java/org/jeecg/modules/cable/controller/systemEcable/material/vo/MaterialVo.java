package org.jeecg.modules.cable.controller.systemEcable.material.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterial;

import java.util.List;


@Schema(description = "系统基础材料名称vo")
@Data
public class MaterialVo {

    public MaterialVo() {
    }

    public MaterialVo(List<EcbMaterial> list, Long count) {
        this.list = list;
        this.count = count;
    }

    public MaterialVo(List<EcbMaterial> list, Long count, EcbMaterial record) {
        this.list = list;
        this.count = count;
        this.record = record;
    }

    @Schema(description = "方案列表")
    private List<EcbMaterial> list;

    @Schema(description = "数量")
    private Long count;

    @Schema(description = "包带")
    private EcbMaterial record;
}
