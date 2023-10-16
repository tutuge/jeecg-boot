package org.jeecg.modules.cable.entity.systemCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCompany {
    private Integer ecdcId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Boolean defaultType;//是否默认

    private String abbreviation;//公司简称

    private String fullName;//公司全称

    private String logoImg;//logo图片

    private String sealImg;//印章图片

    private Integer billPercentType;//发票税点类型

    private String description;//备注
}
