package org.jeecg.modules.cable.entity.userOffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuoArea {
    private Integer ecuoaId;//主键ID

    private Integer ecqulId;//质量等级ID

    private Integer sortId;//序号

    private String areaStr;//截面
}
