package org.jeecg.modules.cable.controller.price.input.bo;


import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "通过ecuqiId获取结构体")
@Data
public class InputStructBo {

    @Schema(description = "主键ID")
    @NotNull(message = "主键ID不得为空")
    private Integer ecuqiId;

    @Schema(description = "修改的数据")
    private List<JSONObject> list;

    //@Schema(description = "材料ID")
    //@NotNull(message = "材料ID不得为空")
    //private Integer materialId;
    //
    //@Schema(description = "材料类型ID")
    //@NotNull(message = "材料类型ID不得为空")
    //private Integer materialTypeId;
    //
    //@Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    //@NotNull(message = "材料类型不得为空")
    //private Integer materialType;
    //
    //@Schema(description = "粗芯丝号")
    //private BigDecimal fireSilkNumber;
    //
    //@Schema(description = "细芯丝号")
    //private BigDecimal zeroSilkNumber;
    //
    ////--------------------内部材料-----------------
    //@Schema(description = "粗芯材料厚度")
    //private BigDecimal fireThickness;
    //
    //@Schema(description = "细芯材料厚度")
    //private BigDecimal zeroThickness;
    //
    ////--------------------外部材料-----------------
    //
    //@Schema(description = "材料厚度")
    //private BigDecimal thickness;
    //
    //@Schema(description = "系数")
    //private BigDecimal factor;

}
