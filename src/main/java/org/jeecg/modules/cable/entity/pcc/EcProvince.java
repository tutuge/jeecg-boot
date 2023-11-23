package org.jeecg.modules.cable.entity.pcc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ec_province")
public class EcProvince {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecpId;//主键ID
    @Schema(description = "管理员ID")
    private Integer ecaId;//管理员ID
    @Schema(description = "管理员名称")
    private String ecaName;//管理员名称
    @Schema(description = "是否启用")
    private Boolean startType;//是否启用
    @Schema(description = "序号")
    private Integer sortId;//序号
    @Schema(description = "省名称")
    private String provinceName;//省名称
    @Schema(description = "备注")
    private String description;//备注
    @Schema(description = "添加时间")
    private Long addTime;//添加时间
    @Schema(description = "修改时间")
    private Long updateTime;//修改时间


    @Schema(description = "城市")
    @TableField(exist = false)
    private EcCity ecCity;//城市

    @Schema(description = "县")
    @TableField(exist = false)
    private EcCounty ecCounty;//县
}
