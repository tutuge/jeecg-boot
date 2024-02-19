package org.jeecg.modules.cable.controller.userEcable.material.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;

import java.util.List;


@Schema(description = "用户基础材料名称vo")
@Data
public class MaterialTypeVo {

    public MaterialTypeVo() {
    }

    public MaterialTypeVo(List<EcbuMaterialType> list, Long count) {
        this.list = list;
        this.count = count;
    }

    public MaterialTypeVo(List<EcbuMaterialType> list, Long count, EcbuMaterialType record) {
        this.list = list;
        this.count = count;
        this.record = record;
    }

    @Schema(description = "材料类型")
    private List<EcbuMaterialType> list;

    @Schema(description = "数量")
    private Long count;

    @Schema(description = "包带")
    private EcbuMaterialType record;
}
