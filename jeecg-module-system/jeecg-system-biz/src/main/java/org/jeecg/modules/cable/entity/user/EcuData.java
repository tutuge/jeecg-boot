package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuData {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer ecudId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "用户ID")
    private Integer ecuId;// 用户ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "仓库ID")
    private Integer ecbusId;// 仓库ID

    @Schema(description = "丝名称")
    private String silkName;// 丝名称

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "修改时间")
    private Long updateTime;// 修改时间

    @TableField(exist = false)
    private Integer startNumber;
    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = "仓库信息")
    @TableField(exist = false)
    private EcbuStore ecbuStore;

    @Schema(description = "用户信息")
    @TableField(exist = false)
    private EcUser ecUser;
}
