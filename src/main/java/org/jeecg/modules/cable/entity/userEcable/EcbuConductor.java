package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;

import java.math.BigDecimal;

@Schema(description = "用户导体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuConductor {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbucId;//主键ID

    @Schema(description = "系统导体ID")
    private Integer ecbcId;//系统导体ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "自定义名称")
    private String name;//自定义名称

    @Schema(description = "单价")
    private BigDecimal unitPrice;//单价

    @Schema(description = "密度")
    private BigDecimal density;//密度

    @Schema(description = "电阻")
    private BigDecimal resistivity;//电阻

    @Schema(description = "详情")
    private String description;//详情

    @Schema(description = "系统导体")
    private EcbConductor ecbConductor;//系统导体

}
