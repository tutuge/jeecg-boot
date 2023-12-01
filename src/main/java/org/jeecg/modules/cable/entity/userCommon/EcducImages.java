package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Schema(description = "公司图片信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcducImages {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecduciId;

    @Schema(description = "公司ID")
    private Integer ecducId;

    @Schema(description = "图片路径")
    private String imageUrl;

    @Schema(description = "添加时间")
    private Date addTime;

    @TableField(exist = false)
    @Schema(description = "图片位置")
    private EcduciPosition ecduciPosition;
}
