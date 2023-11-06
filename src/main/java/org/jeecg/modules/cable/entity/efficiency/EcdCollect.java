package org.jeecg.modules.cable.entity.efficiency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCollect {
    private Integer ecdtId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer typeId;//数据类型

    private String txtUrl;//txt文件地址

    private Long effectTime;//影响时间
}
