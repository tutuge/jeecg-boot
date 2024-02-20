package org.jeecg.modules.cable.entity.systemEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "系统材料")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbMaterials {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "管理员ID")
    private Integer ecaId;

    @Schema(description = "管理员名称")
    private String ecaName;

    @Schema(description = "材料ID")
    private Integer materialTypeId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "1 铜 2 铝")
    private Integer conductorType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "简称")
    private String abbreviation;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "密度")
    private BigDecimal density;

    @Schema(description = "电阻")
    private BigDecimal resistivity;

    @Schema(description = "详情")
    private String description;// 详情

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "修改时间")
    private Date updateTime;// 修改时间

    //@Schema(description = "用户导体")
    //@TableField(exist = false)
    //private EcbuConductor ecbuConductor;// 用户导体

    @Schema(description = "公司ID")
    @TableField(exist = false)
    private Integer ecCompanyId;// 公司ID
}
