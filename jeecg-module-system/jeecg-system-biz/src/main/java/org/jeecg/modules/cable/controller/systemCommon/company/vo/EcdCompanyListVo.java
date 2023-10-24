package org.jeecg.modules.cable.controller.systemCommon.company.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCompanyListVo {

    @Schema(description = "列表")
    private List<EcdCompany> list;

    @Schema(description = "数量")
    private Long count;
}
