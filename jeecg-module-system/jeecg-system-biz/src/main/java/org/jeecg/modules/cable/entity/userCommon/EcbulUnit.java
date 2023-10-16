package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbulUnit {
    private Integer ecbuluId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String lengthName;//长度名称

    private Integer meterNumber;//米数

    private String description;//备注
}
