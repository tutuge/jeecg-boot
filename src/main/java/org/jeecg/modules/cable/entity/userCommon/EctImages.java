package org.jeecg.modules.cable.entity.userCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EctImages {
    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ectiId;// 主键ID

    @Schema(description = "类型")
    private Integer typeId;// 类型

    @Schema(description = "用户ID")
    private Integer ecuId;// 用户ID

    @Schema(description = "图片地址")
    private String imageUrl;// 图片地址

    @Schema(description = "添加时间")
    private Date addTime;// 添加时间
}
