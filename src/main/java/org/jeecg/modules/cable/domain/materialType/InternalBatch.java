package org.jeecg.modules.cable.domain.materialType;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InternalBatch extends MaterialTypeBatch{
    //
    //@Schema(description = "粗芯材料厚度")
    //private BigDecimal fireThickness;
    //
    //@Schema(description = "细芯材料厚度")
    //private BigDecimal zeroThickness;
    //
    //@Schema(description = "系数")
    //private BigDecimal factor;

    private List<JSONObject> list;
}
