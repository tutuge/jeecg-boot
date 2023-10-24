package org.jeecg.modules.cable.controller.systemCommon.pcompany.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "平台")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbPcompanySortBo {
    @Schema(description = "主键ID")
    private Integer ecbpId;//主键ID

    @Schema(description = "排序")
    private Integer sortId;

}
