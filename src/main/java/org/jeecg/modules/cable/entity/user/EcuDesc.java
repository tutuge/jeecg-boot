package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.entity.SysUser;

@Schema(description = "备注管理")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuDesc {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecudId;

    @Schema(description = "型号ID")
    private String ecusmId;

    @Schema(description = "芯数字符串")
    private String coreStr;// 芯数字符串

    @Schema(description = "平方数")
    private String areaStr;// 平方数

    @Schema(description = "公司ID")
    private Integer ecCompanyId;// 公司ID

    @Schema(description = "用户ID")
    private Integer ecuId;// 用户ID

    @Schema(description = "是否默认")
    private Boolean defaultType;// 是否默认

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "内容")
    private String content;// 内容

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "修改时间")
    private Long updateTime;// 修改时间

    @TableField(exist = false)
    private Integer startNumber;

    @TableField(exist = false)
    private Integer pageNumber;

    @TableField(exist = false)
    private SysUser sysUser;// 用户
}
