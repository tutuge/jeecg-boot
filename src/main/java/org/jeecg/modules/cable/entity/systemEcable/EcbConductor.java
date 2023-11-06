package org.jeecg.modules.cable.entity.systemEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

import java.math.BigDecimal;

@Schema(description = "系统导体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbConductor {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbcId;// 主键ID

    @Schema(description = "管理员ID")
    private Integer ecaId;// 管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;// 管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "简称")
    private String abbreviation;// 简称

    @Schema(description = "全称")
    private String fullName;// 全称

    @Schema(description = "单价")
    private BigDecimal unitPrice;// 单价

    @Schema(description = "密度")
    private BigDecimal density;// 密度

    @Schema(description = "电阻")
    private BigDecimal resistivity;// 电阻

    @Schema(description = "详情")
    private String description;// 详情

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "修改时间")
    private Long updateTime;// 修改时间

    @Schema(description = "用户导体")
    @TableField(exist = false)
    private EcbuConductor ecbuConductor;// 用户导体

    @Schema(description = "公司ID")
    @TableField(exist = false)
    private Integer ecCompanyId;// 公司ID
}
