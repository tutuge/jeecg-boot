package org.jeecg.modules.cable.controller.userEcable.offer.bo;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.jeecg.modules.cable.domain.computeBo.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "方案")
public class EcuOfferInsertBo {

    @Schema(description = "主键ID")
    private Integer ecuoId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "质量等级ID")
    @NotNull(message = "质量等级ID不得为空")
    private Integer ecqulId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "截面")
    @NotBlank(message = "标称截面不得为空")
    private String areaStr;

    @Schema(description = "成本加点")
    private BigDecimal addPercent;

    @Schema(description = "材料的json")
    private String material;

    @Schema(description = "默认重量")
    private BigDecimal defaultWeight;

    @Schema(description = "默认金额")
    private BigDecimal defaultMoney;

    //-----------------------生成材料对象----------------------------

    @Schema(description = "导体")
    @TableField(exist = false)
    private Conductor conductor;

    @Schema(description = "内部材料")
    @TableField(exist = false)
    private List<Internal> internals;

    @Schema(description = "填充物")
    @TableField(exist = false)
    private Infilling infilling;

    @Schema(description = "外部材料")
    @TableField(exist = false)
    private List<External> externals;

    public String getMaterial() {
        Structure structure = new Structure();
        structure.setConductor(this.conductor);
        structure.setInternals(this.internals);
        structure.setInfilling(this.infilling);
        structure.setExternals(this.externals);
        this.material = JSONObject.toJSONString(structure);
        return material;
    }

}
