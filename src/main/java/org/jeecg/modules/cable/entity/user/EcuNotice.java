package org.jeecg.modules.cable.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;

@Schema(description = "用户备注")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuNotice {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecunId;// 主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "用户ID")
    private Integer ecuId;

    @Schema(description = "是否默认")
    private Boolean defaultType;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "注意事项名称")
    private String noticeName;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "添加时间")
    private Long addTime;

    @Schema(description = "修改时间")
    private Long updateTime;

    @TableField(exist = false)
    private Integer startNumber;

    @TableField(exist = false)
    private Integer pageNumber;

    @Schema(description = "用户")
    @TableField(exist = false)
    private EcUser ecUser;// 用户
}
