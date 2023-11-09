package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "利润管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcProfit {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecpId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "利润名称")
    private String profitName;// 利润名称

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "型号")
    private String ecusmId;// 型号

    @Schema(description = "平方数")
    private String area;// 平方数

    @Schema(description = "芯数字符串")
    private String coreStr;// 芯数字符串

    @Schema(description = "销售数量 起")
    private Integer startNumber;// 销售数量 起

    @Schema(description = "销售数量 止")
    private Integer endNumber;// 销售数量 止

    @Schema(description = "单位ID")
    private Integer ecbuluId;// 单位

    @Schema(description = "单价 开始")
    private BigDecimal startUnitPrice;// 单价 开始

    @Schema(description = "单价 结束")
    private BigDecimal endUnitPrice;// 单价 结束

    @Schema(description = "利润")
    private BigDecimal profit;// 利润

    // @Schema(description = "去除的丝型号名称")
    // private String exceptSilkName;// 去除的丝型号名称

    @Schema(description = "备注")
    private String description;// 备注

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "修改时间")
    private Date updateTime;// 修改时间

    @TableField(exist = false)
    private Integer startNum;

    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcquLevel ecquLevel;// 质量等级

    @Schema(description = "单位")
    @TableField(exist = false)
    private EcbulUnit ecbulUnit;// 单位
}
