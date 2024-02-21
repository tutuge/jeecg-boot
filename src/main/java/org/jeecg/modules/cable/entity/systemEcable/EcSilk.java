package org.jeecg.modules.cable.entity.systemEcable;

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

import java.util.Date;
import java.util.List;

/**
 * 型号类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcSilk {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecsId;

    @Schema(description = "管理员ID")
    private Integer ecaId;

    @Schema(description = "管理员名称")
    private String ecaName;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "简称")
    @NotBlank(message = "简称不得为空", groups = {AddGroup.class})
    private String abbreviation;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "介绍")
    private String description;

    @Schema(description = "材料排序的json字符串")
    @NotBlank(message = "材料排序不得为空", groups = {AddGroup.class})
    private String material;

    public void setMaterial(String material) {
        this.material = material;
        if (StrUtil.isNotBlank(material)) {
            this.materialTypesList = JSONObject.parseArray(material, EcbMaterialType.class);
        }
    }

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "材料类型")
    @TableField(exist = false)
    private List<EcbMaterialType> materialTypesList;

    public void setMaterialTypesList(List<EcbMaterialType> materialTypesList) {
        this.materialTypesList = materialTypesList;
        if (CollUtil.isNotEmpty(materialTypesList)) {
            this.material = JSONObject.toJSONString(materialTypesList);
        }
    }
}
