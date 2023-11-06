package org.jeecg.modules.cable.controller.userCommon.company.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "平台公司列表")
public class CompanyVo {

    @Schema(description = "平台公司列表")
    private List<EcduCompany> list;

    @Schema(description = "数量")
    private Long count;
}
