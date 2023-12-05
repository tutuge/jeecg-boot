package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "客户公司默认第三方平台加点设置")
public class EcbuPlatformCompany {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbupId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "平台类型ID")
    private Integer platformId;

    @Schema(description = "平台名称")
    private String pcName;

    @Schema(description = "平台加点")
    private BigDecimal pcPercent;

    @Schema(description = "备注")
    private String description;
}
