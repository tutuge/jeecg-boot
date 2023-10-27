package org.jeecg.modules.cable.controller.user.udesc.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "备注管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuDescDealBo {

    @Schema(description = "主键ID")
    private Integer ecudId;

    @Schema(description = "内容")
    @NotBlank(message = "内容不得为空")
    private String content;//内容

    @Schema(description = "型号ID")
    private Integer ecsId;

    @Schema(description = "芯数字符串")
    private String coreStr;//芯数字符串

    @Schema(description = "平方数")
    private String areaStr;// 平方数

}
