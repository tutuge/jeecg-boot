package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbusAttribute {
    private Integer ecbusaId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Boolean pcShowOrHide;//铜利润显示或隐藏

    private Boolean paShowOrHide;//铝利润显示或隐藏

    private Boolean dmShowOrHide;//运费加点显示或隐藏
}
