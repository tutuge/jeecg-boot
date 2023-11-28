package org.jeecg.modules.cable.entity.userDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户默认物流或快递类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudDelivery {
    /**
     * 用户默认物流或快递类型
     */
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbuddId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "序号")
    private Integer sortId;
}
