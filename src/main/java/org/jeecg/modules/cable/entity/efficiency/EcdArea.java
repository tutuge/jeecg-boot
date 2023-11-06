package org.jeecg.modules.cable.entity.efficiency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdArea {
    private Integer ecdaId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecqulId;//质量等级ID

    private String txtUrl;//txt文件路径

    private Long effectTime;//影响时间
}
