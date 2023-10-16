package org.jeecg.modules.cable.entity.systemEcable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcSilk {
    private Integer ecsId;//主键ID

    private Integer ecaId;//管理员ID

    private String ecaName;//管理员名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String abbreviation;//简称

    private String fullName;//全称

    private String description;//

    private Long addTime;//添加时间

    private Long updateTime;//更新时间
}
