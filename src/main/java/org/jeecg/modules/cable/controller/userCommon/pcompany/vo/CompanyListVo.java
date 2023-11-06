package org.jeecg.modules.cable.controller.userCommon.pcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "平台公司列表")
public class CompanyListVo {

    @Schema(description = "平台公司列表")
    private List<EcbuPCompanyVo> list;

    @Schema(description = "数量")
    private Long count;
}
