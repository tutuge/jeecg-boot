package org.jeecg.modules.cable.entity.systemEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "系统材料名称")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbMaterialType {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "管理员ID")
    private Integer ecaId;

    @Schema(description = "管理员名称")
    private String ecaName;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "备注")
    private String description;
    
    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    private Integer materialType;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
