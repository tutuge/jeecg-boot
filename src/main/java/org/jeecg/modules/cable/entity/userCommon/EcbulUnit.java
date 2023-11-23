package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "基础数据中的单位管理")
public class EcbulUnit {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbuluId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "排序")
    private Integer sortId;//序号

    @Schema(description = "长度名称")
    private String lengthName;//长度名称

    @Schema(description = "米数")
    private Integer meterNumber;//米数

    @Schema(description = "备注")
    private String description;//备注
}
