package org.jeecg.modules.cable.entity.userEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuMicatape {
    private Integer ecbumId;//主键ID

    private Integer ecbmId;//系统云母带ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private String name;//自定义名称

    private BigDecimal unitPrice;//单价

    private BigDecimal density;//密度

    private String description;//备注

    private EcbMicatape ecbMicatape;//系统云母带
}
