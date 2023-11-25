package org.jeecg.modules.cable.entity.efficiency;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcduPcc {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecdupId;// 主键ID

    @Schema(description = "类型 ID 1 省市县 2 省")
    private Integer typeId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "txt文件路径")
    private String txtUrl;// txt文件路径

    @Schema(description = "影响时间")
    private Long effectTime;// 影响时间
}
