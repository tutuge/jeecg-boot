package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

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

    @Schema(description = "模板文件名称")
    private String fileName;

    @Schema(description = "模板文件路径")
    private String path;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
