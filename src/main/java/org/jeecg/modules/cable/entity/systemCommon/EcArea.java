package org.jeecg.modules.cable.entity.systemCommon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "所有的平方数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcArea {

    @Schema(description = "主键ID")
    private Integer ecAreaId;// 主键ID

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "平方数")
    @NotBlank(message = "平方数不得为空")
    private String areaStr;

    @Schema(description = "时间")
    private Date addTime;
}
