package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcblUnit {
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbluId;// 主键ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "长度名称")
    private String lengthName;// 长度名称

    @Schema(description = "米数")
    private Integer meterNumber;// 米数

    @Schema(description = "备注")
    private String description;// 备注
}
