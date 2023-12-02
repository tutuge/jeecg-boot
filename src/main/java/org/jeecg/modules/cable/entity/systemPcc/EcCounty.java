package org.jeecg.modules.cable.entity.systemPcc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCounty {
    private Integer ecCountyId;//主键ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Integer eccId;//城市ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String countyName;//县名称

    private String description;//备注

    private Date addTime;//添加时间

    private Date updateTime;//更新时间
}
