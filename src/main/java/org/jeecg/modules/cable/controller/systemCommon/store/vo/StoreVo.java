package org.jeecg.modules.cable.controller.systemCommon.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "系统仓库列表")
public class StoreVo {

    @Schema(description = "系统仓库列表")
    private List<EcbStore> list;

    @Schema(description = "数量")
    private Long count;
}
