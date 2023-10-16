package org.jeecg.modules.cable.entity.user;

import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EccUnit {
    private Integer eccuId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String silkName;//丝型号

    private Integer ecbuluId;//单位

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private Integer startNumber;

    private Integer pageNumber;

    private EcbulUnit ecbulUnit;//单位
}
