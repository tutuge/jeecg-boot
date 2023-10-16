package org.jeecg.modules.cable.entity.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuArea {
    private Integer ecuaId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecqulId;//质量等级ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String areaStr;//截面

    private Long effectTime;
}
