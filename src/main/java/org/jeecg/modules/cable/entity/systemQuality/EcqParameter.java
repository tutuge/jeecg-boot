package org.jeecg.modules.cable.entity.systemQuality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "系统参数信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcqParameter {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecqpId;

    @Schema(description = "质量等级ID")
    private Integer ecqlId;

    @Schema(description = "仓库ID")
    private Integer ecbsId;

    @Schema(description = "每米长度")
    private BigDecimal length;

    @Schema(description = "成本加点")
    private BigDecimal cost;

    @Schema(description = "备注")
    private String description;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;


    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "型号类型")
    @TableField(exist = false)
    private EcSilk ecSilk;

    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcqLevel ecqLevel;

    @Schema(description = "仓库")
    @TableField(exist = false)
    private EcbStore ecbStore;

}
