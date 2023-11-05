package org.jeecg.modules.cable.entity.systemDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "快递")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdMoney {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbdmId;// 主键ID

    @Schema(description = "快递ID")
    private Integer ecbdId;// 快递ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "排序")
    private Integer sortId;// 序号

    @Schema(description = "省ID")
    private Integer ecpId;// 省ID

    @Schema(description = "省名称")
    private String provinceName;// 省名称

    @Schema(description = "首重")
    private Integer firstWeight;// 首重

    @Schema(description = "首重金额")
    private BigDecimal firstMoney;// 首重金额

    @Schema(description = "续重金额")
    private BigDecimal continueMoney;// 续重金额
}
