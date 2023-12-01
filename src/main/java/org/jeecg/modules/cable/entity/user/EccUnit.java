package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EccUnit {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer eccuId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "型号ID们")
    private String ecusmId;

    @Schema(description = "型号名称")
    private String silkName;

    @Schema(description = "单位")
    private Integer ecbuluId;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "开始页码")
    @TableField(exist = false)
    private Integer startNumber;

    @Schema(description = "每页数量")
    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = "用户单位")
    @TableField(exist = false)
    private EcbulUnit ecbulUnit;
}
