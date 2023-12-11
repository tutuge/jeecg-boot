package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "单位长度")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcblUnit {
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbluId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "是否默认")
    private Boolean defaultType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "长度名称")
    private String lengthName;

    @Schema(description = "米数")
    private Integer meterNumber;

    @Schema(description = "备注")
    private String description;
}
