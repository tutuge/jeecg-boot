package org.jeecg.modules.cable.domain.material;

import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "材料是否使用")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SilkModelBo {

    @Schema(description = "材料类型ID")
    private Integer id;

    @Schema(description = "材料类型名称")
    private String fullName;

    @Schema(description = "是否启用")
    private Boolean use;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SilkModelBo that = (SilkModelBo) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
