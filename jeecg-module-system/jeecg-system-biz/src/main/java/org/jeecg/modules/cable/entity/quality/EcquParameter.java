package org.jeecg.modules.cable.entity.quality;

import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcquParameter {
    private Integer ecqupId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecqulId;//质量等级ID

    private Integer ecbusId;//仓库ID

    private BigDecimal length;//每米长度

    private BigDecimal cost;//成本加点

    private String description;//备注

    private EcSilk ecSilk;//丝型号

    private EcquLevel ecquLevel;//质量等级

    private EcbuStore ecbuStore;//仓库
}
