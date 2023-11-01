package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "系统平台费率")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbPcompany {
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbpId;// 主键ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "平台类型ID")
    private Integer platformId;// 平台类型ID

    @Schema(description = "平台名称")
    private String pcName;// 平台名称

    @Schema(description = "平台税点")
    private BigDecimal pcPercent;// 平台税点

    @Schema(description = "备注")
    private String description;// 备注
}
