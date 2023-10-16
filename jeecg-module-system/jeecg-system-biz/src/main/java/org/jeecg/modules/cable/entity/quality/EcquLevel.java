package org.jeecg.modules.cable.entity.quality;

import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcquLevel {
    private Integer ecqulId;//主键ID

    private Integer ecsId;//丝型号ID

    private Integer ecbucId;//用户导体ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer powerId;//

    private String name;//自定义名称

    private Boolean defaultType;//是否默认质量等级

    private String description;//备注

    private EcSilk ecSilk;//丝型号

    private EcbuConductor ecbuConductor;//用户导体

    private EcbConductor ecbConductor;//系统导体
}
