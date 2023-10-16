package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EctImages {
    private Integer ectiId;//主键ID

    private Integer typeId;//类型

    private Integer ecuId;//用户ID

    private String imageUrl;//图片地址

    private Long addTime;//添加时间
}
