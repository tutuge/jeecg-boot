package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduciPosition {
    private Integer ecducipId;//主键ID

    private Integer ecduciId;//图片ID

    private String pX;//x轴

    private String pY;//y轴

    private BigDecimal imagePercent;//图片缩放比

    private Long effectTime;//影响时间
}
