package org.jeecg.modules.cable.entity.userDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

@Schema(description = "仓库与运输方式对应表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuDelivery {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbudId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "快递类型")
    private Integer deliveryType;

    @Schema(description = "快递名称")
    private String deliveryName;

    @Schema(description = "简介")
    private String description;

    @Schema(description = "仓库")
    @TableField(exist = false)
    private EcbuStore ecbuStore;
}
