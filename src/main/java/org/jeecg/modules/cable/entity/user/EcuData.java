package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuData {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer ecudId;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "仓库ID")
    private Integer ecbusId;

    @Schema(description = "型号ID")
    private Integer ecusmId;

    @Schema(description = "型号名称")
    @TableField(exist = false)
    private String silkName;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "仓库信息")
    @TableField(exist = false)
    private EcbuStore ecbuStore;

    @Schema(description = "型号信息")
    @TableField(exist = false)
    private EcuSilkModel ecuSilkModel;

    @Schema(description = "用户名称")
    @TableField(exist = false)
    private String ecUsername;
}
