package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "系统材料")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuMaterials {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "材料ID")
    private Integer materialTypeId;

    @Schema(description = "1 铜 2 铝")
    private Integer conductorType;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "简称")
    private String abbreviation;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "密度")
    private BigDecimal density;

    @Schema(description = "电阻")
    private BigDecimal resistivity;

    @Schema(description = "详情")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;
}
