package org.jeecg.modules.cable.controller.price.input.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.domain.computeVo.ConductorVo;
import org.jeecg.modules.cable.domain.computeVo.ExternalVo;
import org.jeecg.modules.cable.domain.computeVo.InfillVo;
import org.jeecg.modules.cable.domain.computeVo.InternalVo;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "通过ecuqiId获取结构体")
@Data
public class InputStructureVo {

    @Schema(description = "导体")
    private ConductorVo conductorVo = new ConductorVo();

    @Schema(description = "内部材料")
    private List<InternalVo> internalVos = new ArrayList<>();

    @Schema(description = "填充物")
    private InfillVo infillVo = new InfillVo();

    @Schema(description = "外部材料")
    private List<ExternalVo> externalVos = new ArrayList<>();

    @Schema(description = "1米的总重量")
    private BigDecimal totalWeight = BigDecimal.ZERO;

    @Schema(description = "1米的总金额")
    private BigDecimal totalMoney = BigDecimal.ZERO;

    @Schema(description = "报价单")
    private EcuqDesc ecuqDesc;

    @Schema(description = "型号")
    private EcuSilkModel ecuSilkModel;
}
