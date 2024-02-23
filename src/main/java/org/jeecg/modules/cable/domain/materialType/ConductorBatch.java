package org.jeecg.modules.cable.domain.materialType;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConductorBatch extends MaterialTypeBatch {

    private List<JSONObject> list;
}
