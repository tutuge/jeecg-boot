package org.jeecg.modules.cable.entity.systemDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "快递")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbdMoney {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbdmId;

    @Schema(description = "快递ID")
    private Integer ecbdId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "省ID")
    private Integer ecpId;

    @Schema(description = "省名称")
    private String provinceName;

    @Schema(description = "首重")
    private Integer firstWeight;

    @Schema(description = "首重金额")
    private BigDecimal firstMoney;

    @Schema(description = "续重金额")
    private BigDecimal continueMoney;

    @Schema(description = "创建日期")
    private Date createTime;

    @Schema(description = "修改日期")
    private Date updateTime;

}
