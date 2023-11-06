package org.jeecg.modules.cable.entity.pcc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcProvince {
    private Integer ecpId;//主键ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String provinceName;//省名称

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private EcCity ecCity;//城市

    private EcCounty ecCounty;//县
}
