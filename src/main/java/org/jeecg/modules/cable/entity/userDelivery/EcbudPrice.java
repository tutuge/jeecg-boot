package org.jeecg.modules.cable.entity.userDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "物流信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudPrice {

    @Schema(description = "物流Id")
    @TableId(type = IdType.AUTO)
    private Integer ecbudpId;

    @Schema(description = "快递Id")
    @NotNull(message = "快递ID不得为空", groups = {AddGroup.class})
    private Integer ecbudId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "省份ID")
    private Integer ecpId;

    private String provinceName;

    private BigDecimal firstPrice;

    private BigDecimal price1;

    private BigDecimal price2;

    private BigDecimal price3;

    private BigDecimal price4;

    private BigDecimal price5;

    // Ec_province
    @Schema(description = "省份信息")
    private EcProvince ecProvince;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;
}
