package org.jeecg.modules.cable.entity.systemEcable;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.modules.cable.domain.material.SilkModelBo;

import java.util.Date;
import java.util.List;

@Schema(description = "用户型号")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcSilkModel {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecsmId;

    @Schema(description = "型号类型ID")
    private Integer ecSilkId;

    @Schema(description = "管理员ID")
    private Integer ecaId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "简称")
    @NotBlank(message = "简称不得为空", groups = {AddGroup.class})
    private String abbreviation;

    @Schema(description = "全称")
    @NotBlank(message = "全称不得为空", groups = {AddGroup.class})
    private String fullName;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "额定电压")
    private String ratedVoltage;

    @Schema(description = "耐压试验")
    private String pressurization;

    @Schema(description = "耐压试验（分钟）")
    private Integer pressurizationTime;

    @Schema(description = "执行标准")
    private Integer standard;


    @Schema(description = "材料是否启用的json字符串")
    private String materialUse;

    public void setMaterialUse(String materialUse) {
        this.materialUse = materialUse;
        this.materialUseList = JSONObject.parseArray(materialUse, SilkModelBo.class);
    }

    @Schema(description = "材料是否启用")
    @TableField(exist = false)
    private List<SilkModelBo> materialUseList;

    @Schema(description = "介绍")
    private String description;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
