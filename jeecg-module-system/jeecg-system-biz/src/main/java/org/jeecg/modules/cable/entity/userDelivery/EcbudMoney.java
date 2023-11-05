package org.jeecg.modules.cable.entity.userDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudMoney {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbudmId;//主键ID

    @Schema(description = "快递ID")
    private Integer ecbudId;//快递ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "排序")
    private Integer sortId;//序号

    @Schema(description = "省ID")
    private Integer ecpId;//省ID

    @Schema(description = "省名称")
    private String provinceName;//省名称

    @Schema(description = "首重")
    private Integer firstWeight;//首重

    @Schema(description = "首重价格")
    private BigDecimal firstMoney;//首重价格

    @Schema(description = "续重价格")
    private BigDecimal continueMoney;//续重价格

    @Schema(description = "省表")
    @TableField(exist = false)
    private EcProvince ecProvince;//省表
}
