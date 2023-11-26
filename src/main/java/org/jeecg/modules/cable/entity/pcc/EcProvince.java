package org.jeecg.modules.cable.entity.pcc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ec_province")
public class EcProvince {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecpId;

    @Schema(description = "管理员ID")
    private Integer ecaId;

    @Schema(description = "管理员名称")
    private String ecaName;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "省名称")
    private String provinceName;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "城市")
    @TableField(exist = false)
    private EcCity ecCity;//城市

    @Schema(description = "县")
    @TableField(exist = false)
    private EcCounty ecCounty;//县
}
