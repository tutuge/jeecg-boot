package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "所有的芯数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcCore {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecCoreId;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "芯数字符串")
    private String coreStr;
}
