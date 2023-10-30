package org.jeecg.modules.cable.entity.efficiency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduPcc {

    private Integer ecdupId;// 主键ID

    private Integer typeId;// 类型 ID 1 快递使用

    private Integer ecCompanyId;// 公司ID

    private String txtUrl;// txt文件路径

    private Long effectTime;// 影响时间
}
