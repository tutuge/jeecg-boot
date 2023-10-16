package org.jeecg.modules.cable.entity.certs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuqCerts {
    private Integer ecuqcId;//主键ID
    private Integer ecCompanyId;//公司ID
    private String certsName;//证书名称
    private Integer ecuId;//用户ID
    private Boolean defaultType;//是否默认
    private Boolean startType;//是否启用
}
