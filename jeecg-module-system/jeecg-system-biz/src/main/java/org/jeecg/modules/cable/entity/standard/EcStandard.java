package org.jeecg.modules.cable.entity.standard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcStandard {
    private Integer ecsId;//主键ID

    private Integer ecssId;//国标类型ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private BigDecimal area;//截面积

    private Integer rootNumber;//根数

    private BigDecimal eResistance;//国标电阻

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//更新时间
}
