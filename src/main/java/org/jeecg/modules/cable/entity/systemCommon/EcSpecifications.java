package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Schema(description = "规格对照表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcSpecifications {

    @TableId(type = IdType.AUTO)
    @Schema(description = "规格ID")
    @Excel(name = "规格ID")
    private Integer specificationsId;

    @Schema(description = "是否是特殊规格")
    @Excel(name = "是否是特殊规格")
    private Boolean special;

    @Schema(description = "简写")
    @Excel(name = "简写")
    private String abbreviation;

    @Schema(description = "全称")
    @Excel(name = "全称")
    private String fullName;

    @Schema(description = "备注")
    @Excel(name = "备注")
    private String description;

    @Schema(description = "添加时间")
    @Excel(name = "添加时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @Schema(description = "更新时间")
    @Excel(name = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
