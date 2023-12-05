package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.poi.excel.annotation.Excel;

import java.util.Date;

@Schema(description = "平台类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuPlatform {

    @Schema(description = "平台类型ID")
    @TableId(type = IdType.AUTO)
    @Excel(name = "主键ID")
    private Integer platformId;

    @Schema(description = "是否启用")
    @Excel(name = "是否启用", replace = {"是_true", "否_false"})
    private Boolean startType;

    @Schema(description = "序号")
    @Excel(name = "序号")
    private Integer sortId;

    @Schema(description = "公司ID")
    @Excel(name = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "平台类型名称")
    @Excel(name = "平台类型名称")
    private String platformName;

    @Schema(description = "备注")
    @Excel(name = "备注")
    private String description;

    @Schema(description = "添加时间")
    @Excel(name = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    @Excel(name = "修改时间")
    private Date updateTime;
}
