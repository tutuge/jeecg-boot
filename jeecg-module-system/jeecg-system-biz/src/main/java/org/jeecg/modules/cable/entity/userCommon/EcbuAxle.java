package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuAxle {
    private Integer ecbuaId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String axleName;//木轴名称

    private BigDecimal axleHeight;//木轴高度

    private BigDecimal circleDiameter;//中心圆直径

    private BigDecimal axleWidth;//轴宽

    private BigDecimal axleDeep;//轴深

    private BigDecimal axleWeight;//轴重

    private BigDecimal axlePrice;//价格

    private String description;//备注
}
