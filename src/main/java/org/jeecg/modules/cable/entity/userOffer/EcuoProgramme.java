package org.jeecg.modules.cable.entity.userOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "成本加点方案")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuoProgramme {


    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer ecuopId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "方案名称")
    private String programmeName;

    @Schema(description = "芯数")
    private String coreStr;

    @Schema(description = "平方数")
    private String areaStr;

    @Schema(description = "加点点数")
    private BigDecimal addPercent;

    @Schema(description = "最低单价")
    private BigDecimal minPrice;

    @Schema(description = "最高单价")
    private BigDecimal maxPrice;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;
}
