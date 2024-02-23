package org.jeecg.modules.cable.domain.materialType;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalBatch extends MaterialTypeBatch{
    //@Schema(description = "系数")
    //private BigDecimal factor;
    //
    //@Schema(description = "材料厚度")
    //private BigDecimal thickness;

    private List<JSONObject> list;
}
