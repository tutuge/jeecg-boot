package org.jeecg.modules.cable.entity.userEcable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "系统材料名称")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbuMaterialType {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "全称")
    private String fullName;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "材料类型 0 普通材料 1 导体 2 填充物")
    private Integer materialType;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EcbuMaterialType type = (EcbuMaterialType) o;
        return Objects.equal(id, type.id) &&
                Objects.equal(ecCompanyId, type.ecCompanyId) &&
                Objects.equal(startType, type.startType) &&
                Objects.equal(fullName, type.fullName) &&
                Objects.equal(materialType, type.materialType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, ecCompanyId, startType, fullName, materialType);
    }
}
