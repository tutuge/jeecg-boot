package org.jeecg.modules.cable.entity.quality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Schema(description = "各个质量等级对应的规格")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuArea {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuaId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "排序")
    private Integer sortId;// 序号

    @Schema(description = "截面")
    private String areaStr;// 截面

    @Schema(description = "生效时间")
    private Long effectTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EcuArea area = (EcuArea) o;
        return Objects.equals(ecCompanyId, area.ecCompanyId)
                && Objects.equals(ecqulId, area.ecqulId)
                && Objects.equals(startType, area.startType)
                && Objects.equals(areaStr, area.areaStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ecCompanyId, ecqulId, startType, areaStr);
    }
}
