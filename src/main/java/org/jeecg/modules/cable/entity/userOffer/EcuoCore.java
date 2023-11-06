package org.jeecg.modules.cable.entity.userOffer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "芯数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuoCore {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuocId;// 主键ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "芯数字符串")
    private String coreStr;// 芯数字符串
}
