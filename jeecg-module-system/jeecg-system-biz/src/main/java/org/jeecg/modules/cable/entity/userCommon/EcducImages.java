package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcducImages {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecduciId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecducId;// 公司ID

    @Schema(description = "图片路径")
    private String imageUrl;// 图片路径

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @TableField(exist = false)
    @Schema(description = "图片位置")
    private EcduciPosition ecduciPosition;
}
