package org.jeecg.modules.cable.entity.userOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "成本加点方案")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuoProgramme {


    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer ecuopId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "方案名称")
    private String programmeName;// 方案名称

    @Schema(description = "芯数")
    private String coreStr;// 芯数

    @Schema(description = "平方数")
    private String areaStr;// 平方数

    @Schema(description = "加点点数")
    private BigDecimal addPercent;// 加点点数

    @Schema(description = "最低单价")
    private BigDecimal minPrice;

    @Schema(description = "最高单价")
    private BigDecimal maxPrice;
}
