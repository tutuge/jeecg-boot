package org.jeecg.modules.cable.controller.systemCommon.pcompany.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "平台费率list")
@Data
public class EcbPcompanyListVo {

    public EcbPcompanyListVo() {
    }

    public EcbPcompanyListVo(List<EcbPlatformCompanyVo> list, Long count) {
        this.list = list;
        this.count = count;
    }

    @Schema(description = "列表")
    private List<EcbPlatformCompanyVo> list;

    @Schema(description = "数量")
    private Long count;
}
