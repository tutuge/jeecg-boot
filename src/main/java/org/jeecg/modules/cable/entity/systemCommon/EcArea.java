package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "所有的平方数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcArea {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecAreaId;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "平方数")
    @NotBlank(message = "平方数不得为空")
    private String areaStr;

    @Schema(description = "时间")
    private Date addTime;
}
