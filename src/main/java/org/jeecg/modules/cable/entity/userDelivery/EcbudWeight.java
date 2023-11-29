package org.jeecg.modules.cable.entity.userDelivery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Schema(description = "快递重量区间设置")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcbudWeight {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecbudmId;

    /**
     * 关联 ecbuDelivery （各个仓库对应的快运/快递）表
     */
    @Schema(description = "快递ID")
    private Integer ecbudId;

    @Schema(description = "开始重量1")
    private Integer startWeight1 = 0;

    @Schema(description = "结束重量1")
    private Integer endWeight1 = 0;

    @Schema(description = "开始重量2")
    private Integer startWeight2 = 0;

    @Schema(description = "结束重量2")
    private Integer endWeight2 = 0;

    @Schema(description = "开始重量3")
    private Integer startWeight3 = 0;

    @Schema(description = "结束重量3")
    private Integer endWeight3 = 0;

    @Schema(description = "开始重量4")
    private Integer startWeight4 = 0;

    @Schema(description = "结束重量4")
    private Integer endWeight4 = 0;

    @Schema(description = "开始重量5")
    private Integer startWeight5 = 0;

    @Schema(description = "结束重量5")
    private Integer endWeight5 = 0;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "更新时间")
    private Date updateTime;

}
