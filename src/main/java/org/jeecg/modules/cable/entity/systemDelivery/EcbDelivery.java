package org.jeecg.modules.cable.entity.systemDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;


@Schema(description = "系统仓库与运输方式对应表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbDelivery {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbdId;

    @Schema(description = "系统仓库ID")
    private Integer ecbsId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "快递类型 1快递 2 快运")
    private Integer deliveryType;

    @Schema(description = "快递名称")
    private String deliveryName;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "仓库")
    @TableField(exist = false)
    private EcbStore ecbStore;
}
