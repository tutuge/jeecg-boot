package org.jeecg.modules.cable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuDesc {
    private Integer ecudId;

    private Integer ecCompanyId;//公司ID

    private Integer ecuId;//用户ID

    private Boolean defaultType;//是否默认

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String content;//内容

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private Integer startNumber;

    private Integer pageNumber;

    private EcUser ecUser;//用户
}
