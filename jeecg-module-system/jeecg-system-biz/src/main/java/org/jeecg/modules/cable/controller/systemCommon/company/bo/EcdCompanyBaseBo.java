package org.jeecg.modules.cable.controller.systemCommon.company.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "公司")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCompanyBaseBo {

    @Schema(description = "主键ID")
    private Integer ecdcId;//主键ID

}
