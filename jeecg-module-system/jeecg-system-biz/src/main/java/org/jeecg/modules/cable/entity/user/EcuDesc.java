package org.jeecg.modules.cable.entity.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;

@Schema(description = "备注管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuDesc {
    @Schema(description = "主键ID")
    private Integer ecudId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "用户ID")
    private Integer ecuId;// 用户ID

    @Schema(description = "是否默认")
    private Boolean defaultType;// 是否默认

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "内容")
    private String content;// 内容

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "修改时间")
    private Long updateTime;// 修改时间

    private Integer startNumber;

    private Integer pageNumber;

    private EcUser ecUser;// 用户
}
