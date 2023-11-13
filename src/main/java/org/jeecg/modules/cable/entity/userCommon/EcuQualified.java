package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "合格证")
@Data
public class EcuQualified {
    @Schema(description = "ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuqId;

    @Schema(description = "创建人")
    private Integer ecuId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "简称")
    private String abbreviation;

    @Schema(description = "型号Id")
    private Integer ecusmId;

    @Schema(description = "规格")
    private String areaStr;

    @Schema(description = "耐压试验")
    private String pressurization;

    @Schema(description = "耐压试验时间（分钟）")
    private Integer pressurizationTime;

    @Schema(description = "全名")
    private String fullName;
    @Schema(description = "执行标准")
    private String standard;

    @Schema(description = "添加时间")
    private Long addTime;//添加时间

    @Schema(description = "更新时间")
    private Long updateTime;//更新时间
}
