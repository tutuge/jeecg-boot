package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "系统税点")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ecd_tax_point")
public class EcdTaxPoint {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecdtId;

    @Schema(description = "管理员ID")
    private Integer ecaId;

    @Schema(description = "管理员名称")
    private String ecaName;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "税点名称")
    private String pointName;

    @Schema(description = "普票税点")
    private BigDecimal percentCommon;

    @Schema(description = "专票税点")
    private BigDecimal percentSpecial;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "用户发票税点")
    @TableField(exist = false)
    private EcduTaxPoint ecduTaxpoint;

    @Schema(description = "公司ID")
    @TableField(exist = false)
    private Integer ecCompanyId;
}
