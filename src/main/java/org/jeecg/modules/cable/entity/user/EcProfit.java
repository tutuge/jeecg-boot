package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "利润管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcProfit {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecpId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "利润名称")
    private String profitName;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "型号ID 以逗号分隔")
    private String ecusmId;

    @Schema(description = "平方数")
    private String area;

    @Schema(description = "芯数字符串")
    private String coreStr;

    @Schema(description = "销售数量 起")
    private Integer startNumber;

    @Schema(description = "销售数量 止")
    private Integer endNumber;

    @Schema(description = "单位ID")
    private Integer ecbuluId;

    @Schema(description = "单价 开始")
    private BigDecimal startUnitPrice;

    @Schema(description = "单价 结束")
    private BigDecimal endUnitPrice;

    @Schema(description = "利润")
    private BigDecimal profit;

    // @Schema(description = "去除的丝型号名称")
    // private String exceptSilkName;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @TableField(exist = false)
    private Integer startNum;

    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;

    @Schema(description = "单位")
    @TableField(exist = false)
    private EcbulUnit ecbulUnit;
}
