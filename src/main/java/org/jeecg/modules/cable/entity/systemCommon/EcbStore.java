package org.jeecg.modules.cable.entity.systemCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "系统仓库")
public class EcbStore {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbsId;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "是否默认仓库")
    private Boolean defaultType;

    @Schema(description = "仓库名称")
    private String storeName;

    @Schema(description = "仓库铜利润")
    private BigDecimal percentCopper;

    @Schema(description = "仓库铝利润")
    private BigDecimal percentAluminium;

    @Schema(description = "工厂到仓库的运费")
    private BigDecimal dunitMoney;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;
}
