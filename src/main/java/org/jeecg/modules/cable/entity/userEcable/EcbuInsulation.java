package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;

import java.math.BigDecimal;

@Schema(description = "用户绝缘")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuInsulation {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbuiId;//主键ID

    @Schema(description = "系统绝缘ID")
    private Integer ecbiId;//系统绝缘ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "自定义名称")
    private String name;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "密度")
    private BigDecimal density;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "系统绝缘")
    @TableField(exist = false)
    private EcbInsulation ecbInsulation;
}
