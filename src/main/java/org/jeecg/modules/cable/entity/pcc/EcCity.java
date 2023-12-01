package org.jeecg.modules.cable.entity.pcc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCity {
    private Integer eccId;//主键ID

    private Integer ecaId;//管理员ID

    private Integer ecpId;//省id

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String cityName;//城市名称

    private String description;//备注

    private Date addTime;//添加时间

    private Date updateTime;//更新时间
}
