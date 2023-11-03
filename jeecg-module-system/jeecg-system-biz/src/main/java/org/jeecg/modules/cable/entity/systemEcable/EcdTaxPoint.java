package org.jeecg.modules.cable.entity.systemEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "系统税点")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ecd_tax_point")
public class EcdTaxPoint {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecdtId;//主键ID

    @Schema(description = "管理员ID")
    private Integer ecaId;//管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;//管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "序号")
    private Integer sortId;//序号

    @Schema(description = "税点名称")
    private String pointName;//税点名称

    @Schema(description = "普票税点")
    private BigDecimal percentCommon;//普票税点

    @Schema(description = "专票税点")
    private BigDecimal percentSpecial;//专票税点

    @Schema(description = "备注")
    private String description;//备注

    @Schema(description = "添加时间")
    private Date addTime;//添加时间

    @Schema(description = "更新时间")
    private Date updateTime;//更新时间

    @Schema(description = "用户发票税点")
    @TableField(exist = false)
    private EcduTaxPoint ecduTaxpoint;//用户发票税点

    @Schema(description = "公司ID")
    @TableField(exist = false)
    private Integer ecCompanyId;//公司ID
}
