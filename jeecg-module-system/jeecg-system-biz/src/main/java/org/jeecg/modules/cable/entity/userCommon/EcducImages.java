package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcducImages {
    private Integer ecduciId;//主键ID

    private Integer ecducId;//公司ID

    private String imageUrl;//图片路径

    private Long addTime;//添加时间

    private EcduciPosition ecduciPosition;
}
