package org.jeecg.modules.cable.domain.materialType;

import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "型号系列")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialTypeBo {

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    private Integer materialType;

    @Schema(description = "是否是内部分屏材料")
    private Boolean merge = Boolean.FALSE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialTypeBo that = (MaterialTypeBo) o;
        return Objects.equal(id, that.id) && Objects.equal(materialType, that.materialType) && Objects.equal(merge, that.merge);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, materialType, merge);
    }
}
