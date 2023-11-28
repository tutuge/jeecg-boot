package org.jeecg.modules.cable.entity.efficiency;

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
public class EcdArea {

    @TableId(type = IdType.AUTO)
    private Integer ecdaId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "txt文件路径")
    private String txtUrl;

    @Schema(description = "影响时间")
    private Date effectTime;
}
