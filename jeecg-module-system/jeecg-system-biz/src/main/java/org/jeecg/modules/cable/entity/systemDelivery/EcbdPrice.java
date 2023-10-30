package org.jeecg.modules.cable.entity.systemDelivery;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdPrice {
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbdpId;// 主键ID

    @Schema(description = "快递ID")
    private Integer ecbdId;// 快递ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "省ID")
    private Integer ecpId;// 省ID

    @Schema(description = "省名称")
    private String provinceName;// 省名称

    @Schema(description = "首重金额")
    private BigDecimal firstPrice;// 首重金额

    @Schema(description = "金额")
    private BigDecimal price1;// 金额

    @Schema(description = "金额")
    private BigDecimal price2;// 金额

    @Schema(description = "金额")
    private BigDecimal price3;// 金额

    @Schema(description = "金额")
    private BigDecimal price4;// 金额

    @Schema(description = "金额")
    private BigDecimal price5;// 金额

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;
}
