package org.jeecg.modules.cable.entity.systemCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcblUnit {
    private Integer ecbluId;//主键ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String lengthName;//长度名称

    private Integer meterNumber;//米数

    private String description;//备注
}
