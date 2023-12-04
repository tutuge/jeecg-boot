package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Schema(description = "账户价格表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcAccount {

    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    @Excel(name = "ID")
    private Integer id;

    @Schema(description = "排序")
    private Integer sortId;

    @Schema(description = "月数")
    @NotNull(message = "开通月数不得为空")
    private Integer duration;

    @Schema(description = "月数的显示")
    @NotBlank(message = "开通时长备注不得为空")
    private String durationDesc;

    @Schema(description = "价格")
    @NotNull(message = "账号价格不得为空")
    private BigDecimal price;

    @Schema(description = "折扣")
    @NotNull(message = "账号折扣不得为空")
    private BigDecimal discount;

    @Schema(description = "子账号价格")
    @NotNull(message = "子账号价格不得为空")
    private BigDecimal sonPrice;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "备注")
    @Excel(name = "备注")
    private String description;

    @Schema(description = "添加时间")
    @Excel(name = "添加时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    @Schema(description = "更新时间")
    @Excel(name = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;
}
