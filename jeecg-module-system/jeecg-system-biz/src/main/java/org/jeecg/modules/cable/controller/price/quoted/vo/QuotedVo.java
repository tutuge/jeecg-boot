package org.jeecg.modules.cable.controller.price.quoted.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "报价列表")
public class QuotedVo {

    @Schema(description = "报价列表")
    private List<EcuQuoted> list;

    @Schema(description = "报价数量")
    private Long count;
}
