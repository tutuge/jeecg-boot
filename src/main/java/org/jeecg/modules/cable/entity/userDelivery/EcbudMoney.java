package org.jeecg.modules.cable.entity.userDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "快递相关价格信息")
public class EcbudMoney {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbudmId;

    /**
     * 来自于ecbuDelivery
     */
    @Schema(description = "快递ID")
    private Integer ecbudId;

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

    @Schema(description = "首重价格")
    private BigDecimal firstMoney;

    @Schema(description = "续重价格")
    private BigDecimal continueMoney;

    @Schema(description = "创建时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "省份表")
    @TableField(exist = false)
    private EcProvince ecProvince;
}
