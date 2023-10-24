package org.jeecg.modules.cable.controller.systemCommon.pcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;

import java.util.List;


@Schema(description = "平台")
@Data
public class EcbPcompanyVo {

    public EcbPcompanyVo() {
    }

    public EcbPcompanyVo(List<EcbPcompany> list, Long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "列表")
    private List<EcbPcompany> list;

    @Schema(description = "数量")
    private Long count;
}
