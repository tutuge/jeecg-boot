package org.jeecg.modules.cable.entity.userEcable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
import org.jeecg.modules.cable.domain.materialType.MaterialTypeBo;

import java.util.Date;
import java.util.List;

@Schema(description = "用户型号系列")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuSilk {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecusId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "公司ID")
    private Integer companyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "简称")
    private String abbreviation;

    @Schema(description = "全称")
    @NotBlank(message = "名称不得为空", groups = {AddGroup.class})
    private String fullName;

    @Schema(description = "介绍")
    private String description;

    @Schema(description = "材料排序的json字符串")
    private String material;

    public void setMaterial(String material) {
        this.material = material;
        if (StrUtil.isNotBlank(material)) {
            this.materialTypesList = JSONObject.parseArray(material, MaterialTypeBo.class);
        }
    }

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "材料类型")
    @TableField(exist = false)
    private List<MaterialTypeBo> materialTypesList;

    public void setMaterialTypesList(List<MaterialTypeBo> materialTypesList) {
        this.materialTypesList = materialTypesList;
        if (CollUtil.isNotEmpty(materialTypesList)) {
            this.material = JSONObject.toJSONString(materialTypesList);
        }
    }
}
