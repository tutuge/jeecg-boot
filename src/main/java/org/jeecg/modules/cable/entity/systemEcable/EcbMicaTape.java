package org.jeecg.modules.cable.entity.systemEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;

import java.math.BigDecimal;


@Schema(description = "系统云母带")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbMicaTape {
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbmId;// 主键ID

    @Schema(description = "管理员ID")
    private Integer ecaId;// 管理员ID

    @Schema(description = "管理员名称")
    private String ecaName;// 管理员名称

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "排序")
    private Integer sortId;// 序号

    @Schema(description = "简称")
    private String abbreviation;// 简称

    @Schema(description = "全称")
    private String fullName;// 全称

    @Schema(description = "单价")
    private BigDecimal unitPrice;// 单价

    @Schema(description = "密度")
    private BigDecimal density;// 密度

    @Schema(description = "备注")
    private String description;// 备注

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "更新时间")
    private Long updateTime;// 更新时间

    @Schema(description = "用户云母带")
    @TableField(exist = false)
    private EcbuMicaTape ecbuMicatape;// 用户云母带

    @Schema(description = "公司ID")
    @TableField(exist = false)
    private Integer ecCompanyId;// 公司ID
}
