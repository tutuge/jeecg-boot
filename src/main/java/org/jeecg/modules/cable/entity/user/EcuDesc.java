package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.entity.SysUser;

import java.util.Date;

/**
 * 备注管理是针对报价单明细数据最后面的备注维护的基础数据。
 * 原则上选择型号、规格之后，如果备注管理维护了相关型号和规格的备注信息，自动带出来。
 */
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
    private String coreStr;

    @Schema(description = "平方数")
    private String areaStr;

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "是否默认")
    private Boolean defaultType;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @TableField(exist = false)
    private Integer startNumber;

    @TableField(exist = false)
    private Integer pageNumber;

    @TableField(exist = false)
    private SysUser sysUser;
}
