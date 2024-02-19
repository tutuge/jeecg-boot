package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcdTaxPoint;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduTaxPoint {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecdutId;

    @Schema(description = "系统发票税点id")
    private Integer ecdtId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "自定义名称")
    private String name;

    @Schema(description = "普票税点")
    private BigDecimal percentCommon;

    @Schema(description = "专票税点")
    private BigDecimal percentSpecial;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "系统发票税点")
    @TableField(exist = false)
    private EcdTaxPoint ecdTaxpoint;
}
