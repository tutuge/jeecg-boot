package org.jeecg.modules.cable.entity.systemEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;

import java.math.BigDecimal;
import java.util.Date;


@Schema(description = "系统填充物")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbInfilling {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbinId;// 主键ID

    @Schema(description = "管理员ID")
    private Integer ecaId;// 管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;// 管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "简介")
    private String abbreviation;// 简介

    @Schema(description = "全称")
    private String fullName;// 全称

    @Schema(description = "单价")
    private BigDecimal unitPrice;// 单价

    @Schema(description = "密度")
    private BigDecimal density;// 密度

    @Schema(description = "备注")
    private String description;// 备注

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "更新时间")
    private Date updateTime;// 更新时间

    @Schema(description = "用户填充物")
    @TableField(exist = false)
    private EcbuInfilling ecbuInfilling;// 用户填充物

    @Schema(description = "公司ID")
    @TableField(exist = false)
    private Integer ecCompanyId;// 公司ID
}
