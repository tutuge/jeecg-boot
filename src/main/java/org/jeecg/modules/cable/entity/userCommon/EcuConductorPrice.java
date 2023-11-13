package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "报价单导体对应价格")
@Data
public class EcuConductorPrice {

    @TableId(type = IdType.AUTO)
    private Integer ecucpId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "用户导体ID")
    private Integer ecbucId;

    @Schema(description = "报价单上面改变的导体价格")
    private BigDecimal cunitPrice;
}
