package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.poi.excel.annotation.Excel;

import java.util.Date;


@Schema(description = "规格对照表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcSpecifications {

    @TableId(type = IdType.AUTO)
    @Schema(description = "规格ID")
    @Excel(name = "规格ID")
    private Integer specificationsId;// 主键ID

    @Schema(description = "规格简写")
    @Excel(name = "规格简写")
    private String areaStr;

    @Schema(description = "备注")
    @Excel(name = "备注")
    private String description;// 备注

    @Schema(description = "添加时间")
    @Excel(name = "添加时间")
    private Date addTime;// 添加时间

    @Schema(description = "更新时间")
    @Excel(name = "更新时间")
    private Date updateTime;// 更新时间
}
