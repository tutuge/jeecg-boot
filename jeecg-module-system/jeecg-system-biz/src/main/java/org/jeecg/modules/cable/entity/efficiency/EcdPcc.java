package org.jeecg.modules.cable.entity.efficiency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdPcc {
    private Integer ecdpId;//主键ID

    private Integer typeId;//类型 1 快递使用

    private String txtUrl;//txt文件路径

    private Long effectTime;//影响时间
}
