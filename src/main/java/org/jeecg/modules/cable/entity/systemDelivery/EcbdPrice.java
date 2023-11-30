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
    private Integer ecbdpId;

    @Schema(description = "快递ID")
    private Integer ecbdId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "省ID")
    private Integer ecpId;

    @Schema(description = "省名称")
    private String provinceName;

    @Schema(description = "首重金额")
    private BigDecimal firstPrice;

    @Schema(description = "金额")
    private BigDecimal price1;

    @Schema(description = "金额")
    private BigDecimal price2;

    @Schema(description = "金额")
    private BigDecimal price3;

    @Schema(description = "金额")
    private BigDecimal price4;

    @Schema(description = "金额")
    private BigDecimal price5;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;
}
