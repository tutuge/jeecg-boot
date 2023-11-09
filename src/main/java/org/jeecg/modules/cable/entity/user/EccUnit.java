package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EccUnit {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer eccuId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "型号")
    private String ecusmId;// 型号

    @Schema(description = "丝型号")
    private String silkName;// 丝型号

    @Schema(description = "单位")
    private Integer ecbuluId;// 单位

    @Schema(description = "备注")
    private String description;// 备注

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "修改时间")
    private Long updateTime;// 修改时间

    @Schema(description = "开始页码")
    @TableField(exist = false)
    private Integer startNumber;

    @Schema(description = "每页数量")
    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = "用户单位")
    @TableField(exist = false)
    private EcbulUnit ecbulUnit;// 单位
}
